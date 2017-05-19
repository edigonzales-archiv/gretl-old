package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.EmptyFileException;
import ch.so.agi.gretl.core.NotAllowedExpressionException;
import ch.so.agi.gretl.logging.Logger;
import ch.so.agi.gretl.core.SqlReader;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by bjsvwsch on 03.05.17.
 */


public class Db2DbStep {

    private Connection sourceDb;
    private Connection targetDb;
    /** KONSTRUKTOR **/
    public Db2DbStep(Connection sourceDb, Connection targetDb) {
        this.sourceDb = sourceDb;
        this.targetDb = targetDb;
    }

    /**
     * Führt für alle Transfersets die Transfers von der Quell- in die Zieldatenbank
     * durch und schliesst die Transaktion ab.
     */
    public void processAllTransferSets(List<TransferSet> transferSets) throws SQLException, FileNotFoundException {
        Logger.log(Logger.INFO_LEVEL, "Found "+transferSets.size()+" transferSets");
        for(TransferSet transferSet : transferSets){
            processTransferSet(sourceDb, targetDb, transferSet);
        }

        try {
            sourceDb.commit();
            targetDb.commit();
            Logger.log(Logger.INFO_LEVEL, "Transaction successful!");
        } catch (SQLException e42) {
            Logger.log(Logger.INFO_LEVEL, "Transaction failed!");
            Logger.log(Logger.DEBUG_LEVEL, e42);
            sourceDb.rollback();
            targetDb.rollback();
        }

    }

    /**
     * Führt für das Transferset den Transfer vom Quell-Resultset in die entsprechende Tabelle
     * der Zieldatenbank durch.
     * @param srcCon
     * @param targetCon
     * @param transferSet
     */
    private void processTransferSet(Connection srcCon, Connection targetCon, TransferSet transferSet) throws SQLException, FileNotFoundException {
        if (transferSet.getDeleteAllRows() == true) {
            deleteDestTableContents(targetCon, transferSet.getOutputQualifiedSchemaAndTableName());
        }
        String selectStatement = extractSingleStatement(transferSet.getInputSqlFile());

        PreparedStatement insertRowStatement = createInsertRowStatement(
                   srcCon,
                   selectStatement,
                   transferSet.getOutputQualifiedSchemaAndTableName());

        ResultSet rs = createResultSet(srcCon, selectStatement);

        while (rs.next()) {
            int size = 0;
            transferRow(rs, insertRowStatement, size);
        }
    }

    /**
     * Kopiert eine Zeile des Quell-ResultSet in die Zieltabelle
     * @param rs
     * @param insertRowStatement
     * @param size
     */
    private void transferRow(ResultSet rs, PreparedStatement insertRowStatement, int size) throws SQLException {
        size++;
        // insert
        // assign column wise values
        int columncount = rs.getMetaData().getColumnCount();
        for (int j = 1; j < columncount; j++) {
            insertRowStatement.setObject(j,rs.getObject(j));
        }
        insertRowStatement.execute();
    }

    private void deleteDestTableContents(Connection targetCon, String destTableName) throws SQLException {
        String sqltruncate = "DELETE FROM "+destTableName;
        Logger.log(Logger.INFO_LEVEL,"Try to delete all rows in Table "+destTableName);
        try {
            PreparedStatement truncatestmt = targetDb.prepareStatement(sqltruncate);
            truncatestmt.execute();
            Logger.log(Logger.INFO_LEVEL, "DELETE succesfull!");
        } catch (SQLException e1) {
            Logger.log(Logger.INFO_LEVEL, "DELETE FROM TABLE "+destTableName+" failed!");
            Logger.log(Logger.DEBUG_LEVEL, e1);
            throw e1;
        }
    }



    /**
     * Erstellt mittels Quell-SelectStatement auf die Quelldatenbank das ResultSet.
     */
    private ResultSet createResultSet(Connection srcCon, String sqlSelectStatement) throws SQLException {
        Statement SQLStatement = sourceDb.createStatement();
        ResultSet rs = SQLStatement.executeQuery(sqlSelectStatement);

        return rs;
    }

    /**
     * Erstellt aufgrund der Metadaten des Quell-SelectStatements das Statement für den Insert einer
     * Zeile in die Zieltabelle
     * @param srcCon
     * @param srcQuery
     * @param destTableName
     * * @return
     */

    private PreparedStatement createInsertRowStatement(Connection srcCon, String srcQuery, String destTableName) throws SQLException {
        ResultSetMetaData meta = null;
        Statement dbstmt = null;
        StringBuilder columnNames = null;
        StringBuilder bindVariables = null;
        ResultSet rs = null;
        try {
            Statement SQLStatement = sourceDb.createStatement();
            rs = SQLStatement.executeQuery(srcQuery);
        } catch (SQLException f) {
            Logger.log(Logger.INFO_LEVEL, String.valueOf(f));
            throw new SQLException(f);
        }
        try {
            meta = rs.getMetaData();
        } catch (SQLException g) {
            Logger.log(Logger.INFO_LEVEL, String.valueOf(g));
            throw new SQLException(g);
        }
        columnNames = new StringBuilder();
        bindVariables = new StringBuilder();

        int j;
        for (j = 1; j < meta.getColumnCount(); j++)
        {
            if (j > 1) {
                columnNames.append(", ");
                bindVariables.append(", ");
            }
            columnNames.append(meta.getColumnName(j));
            bindVariables.append("?");
        }
        Logger.log(Logger.INFO_LEVEL, "I got "+j+" columns");
        // prepare destination sql
        String sql = "INSERT INTO " + destTableName + " ("
                + columnNames
                + ") VALUES ("
                + bindVariables
                + ")";
        Logger.log(Logger.DEBUG_LEVEL,"INSERT STATEMENT RAW = "+sql);

        PreparedStatement insertRowStatement = targetDb.prepareStatement(sql);

        return insertRowStatement;
    }

    private String extractSingleStatement(File targetFile) throws FileNotFoundException {
        if(!targetFile.canRead()) {throw new FileNotFoundException();}
        FileReader read = new FileReader(targetFile);
        PushbackReader reader = null;
        reader = new PushbackReader(read);
        String line = null;

        /** LIST of forbidden words **/
        List<String> keywords = new ArrayList<>();
        keywords.add("INSERT");
        keywords.add("DELETE");
        keywords.add("UPDATE");
        keywords.add("DROP");
        keywords.add("CREATE");

        String firstline = null;
        try {
            line = SqlReader.readSqlStmt(reader);
            if(line == null) {
                Logger.log(Logger.INFO_LEVEL,"Empty File. No Statement to execute!");
                throw new EmptyFileException();
            }
            while (line != null) {
                firstline = line.trim();
                if (firstline.length() > 0) {
                    Logger.log(Logger.INFO_LEVEL, "Statement found. Length: " + firstline.length()+" caracters");
                    //Check if there are no bad words in the Statement
                    if (containsAKeyword(firstline, keywords) == true) {
                        Logger.log(Logger.INFO_LEVEL, "FOUND NOT ALLOWED WORDS IN SQL STATEMENT!");
                        throw new NotAllowedExpressionException();
                    }
                } else {
                    Logger.log(Logger.INFO_LEVEL, "NO STATEMENT IN FILE!");
                    throw new FileNotFoundException();
                }
                // read next line
                line = SqlReader.readSqlStmt(reader);
                if (line != null) {
                    Logger.log(Logger.INFO_LEVEL, "There are more then 1 SQL-Statement in the file " + targetFile.getName() + " but only the first Statement will be executed!");
                    throw new RuntimeException();
                }

            }
        } catch (IOException e2) {
            throw new IllegalStateException(e2);
        } finally {
            try {
                reader.close();
            } catch (IOException e3) {
                throw new IllegalStateException(e3);
            }
        }
        return firstline;
    }

    private boolean containsAKeyword(String myString, List<String> keywords){
        for(String keyword : keywords){
            if(myString.contains(keyword)){
                return true;
            }
        }
        return false; // Never found match.
    }



}