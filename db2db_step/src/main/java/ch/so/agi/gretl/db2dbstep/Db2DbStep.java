package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.Logger;
import ch.so.agi.gretl.core.SqlReader;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStep {

    /** KONSTRUKTOR **/

    /** HAUPTFUNKTION **/

    public void execute(Connection sourceDb, Connection targetDb, List<TransferSet> transferSets) throws SQLException {

        /** LIST of forbidden words **/
        List<String> keywords = new ArrayList<>();
        keywords.add("INSERT");
        keywords.add("DELETE");
        keywords.add("UPDATE");
        keywords.add("DROP");
        keywords.add("CREATE");

        String e = null;
        Logger.log(Logger.INFO_LEVEL, "Found "+transferSets.size()+" transferSets");
        for (TransferSet transferSet: transferSets) {

            Logger.log(Logger.DEBUG_LEVEL, Boolean.toString(transferSet.getTruncate()));
            Logger.log(Logger.DEBUG_LEVEL, transferSet.getInputSqlFile().toString());
            Logger.log(Logger.DEBUG_LEVEL, transferSet.getOutputQualifiedSchemaAndTableName());

            File InputFile = transferSet.getInputSqlFile();

            if (transferSet.getTruncate()==true) {
                truncateTargetTable(targetDb, transferSet);
            }

            try {
                readInputFile(sourceDb, targetDb, keywords, e, transferSet, InputFile);
            } catch (IOException e4) {
                Logger.log(Logger.INFO_LEVEL, "Could not read sql file!");
                throw new RuntimeException(e4);
            }
        } //End of For-loop
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

    private void readInputFile(Connection sourceDb, Connection targetDb, List<String> keywords, String e, TransferSet transferSet, File inputFile) throws FileNotFoundException {
        FileReader read = new FileReader(inputFile);
        PushbackReader reader = null;
        reader = new PushbackReader(read);
        try {
            String line = SqlReader.readSqlStmt(reader);
            while (line != null) {
                line = line.trim();
                if (line.length() > 0) {
                    Logger.log(Logger.INFO_LEVEL,"Statements: "+line.length());
                    //Check if there are no bad words in the Statement
                    if (containsAKeyword(line, keywords) == true) {
                        Logger.log(Logger.INFO_LEVEL, "FOUND NOT ALLOWED WORDS IN SQL STATEMENT!");
                        throw new IOException(e);
                    }
                    try {
                        //HIER WIRD RICHTIG AUSGEFÜHRT!!!
                        executeSql(sourceDb, targetDb, line, transferSet);
                    } catch (Exception e1) {
                      Logger.log(Logger.DEBUG_LEVEL, e1);
                      throw new IOException(e1);
                    }

                }
                // read next line
                line = SqlReader.readSqlStmt(reader);
                if (line != null) {
                    Logger.log(Logger.INFO_LEVEL,"There are more then 1 SQL-Statement in the file "+ inputFile.getName()+" but only the first Statement will be executed!");
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
    }

    private void truncateTargetTable(Connection targetDb, TransferSet transferSet) throws SQLException {
        String sqltruncate = "DELETE FROM "+transferSet.getOutputQualifiedSchemaAndTableName();
        Logger.log(Logger.INFO_LEVEL,"Try to delete all rows in Table "+transferSet.getOutputQualifiedSchemaAndTableName());
        try {
            //targetDb.setAutoCommit(true);
            PreparedStatement truncatestmt = targetDb.prepareStatement(sqltruncate);
            truncatestmt.execute();
            Logger.log(Logger.INFO_LEVEL, "DELETE succesfull!");
        } catch (SQLException e1) {
            Logger.log(Logger.INFO_LEVEL, "DELETE FROM TABLE "+transferSet.getOutputQualifiedSchemaAndTableName()+" failed!");
            Logger.log(Logger.DEBUG_LEVEL, e1);
            throw e1;
        }
    }

    private void executeSql(Connection sourceDb, Connection targetDb, String line, TransferSet set) throws Exception {
        Statement dbstmt = null;
        StringBuilder columnNames = null;
        StringBuilder bindVariables = null;

        try{
            try{
                dbstmt = sourceDb.createStatement();
                Logger.log(Logger.DEBUG_LEVEL, line);
                ResultSet rs = null;
                Statement SQLStatement = sourceDb.createStatement();

                try {
                    rs = SQLStatement.executeQuery(line);
                } catch (SQLException f) {
                    Logger.log(Logger.INFO_LEVEL, String.valueOf(f));
                    throw new SQLException(f);
                }

                //Metadaten werden präpariert (column names)
                ResultSetMetaData meta = null;
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
                String sql = "INSERT INTO " + set.getOutputQualifiedSchemaAndTableName() + " ("
                        + columnNames
                        + ") VALUES ("
                        + bindVariables
                        + ")";
                Logger.log(Logger.DEBUG_LEVEL,"INSERT STATEMENT RAW = "+sql);


                //Write it to the Output-Table
                // for all records in source recordset
                int size = 0;
                while(rs.next()){
                    size++;
                    // insert
                    // assign column wise values
                    PreparedStatement deststmt = targetDb.prepareStatement(sql);
                    for (j = 1; j < meta.getColumnCount(); j++) {
                        deststmt.setObject(j,rs.getObject(j));
                    }
                    deststmt.execute();
                }
                Logger.log(Logger.INFO_LEVEL, "I got "+size+" rows");
            } catch (SQLException e1) {
                Logger.log(Logger.DEBUG_LEVEL,e1);
                throw e1;
            }
        } catch (RuntimeException e33) {
            Logger.log(Logger.DEBUG_LEVEL, "Function executeSql failed");
        }
    }

    public boolean containsAKeyword(String myString, List<String> keywords){
        for(String keyword : keywords){
            if(myString.contains(keyword)){
                return true;
            }
        }
        return false; // Never found match.
    }
}