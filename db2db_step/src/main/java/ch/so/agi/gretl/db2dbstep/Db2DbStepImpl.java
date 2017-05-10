package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.DbConnectorImp;
import ch.so.agi.gretl.core.Logger;
import ch.so.agi.gretl.core.LoggerImp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImpl
    implements Db2DbStep {

    /** KONSTRUKTOR **/

    /** HAUPTFUNKTION **/

    @Override
    public void execute(Connection sourceDb, Connection targetDb, List<TransferSet> transferSets) throws IOException {
        Logger Db2DbLog = LoggerImp.getInstance();

        /** LIST of forbidden words **/
        List<String> keywords = new ArrayList<>();
        keywords.add("INSERT");
        keywords.add("DELETE");
        keywords.add("UPDATE");
        keywords.add("DROP");
        keywords.add("CREATE");

        String e = null;

        for (int i = 0; i < transferSets.size(); i++) {

            System.out.println(transferSets.get(i).getTruncate());
            System.out.println(transferSets.get(i).getInputSqlFile());
            System.out.println(transferSets.get(i).getOutputQualifiedSchemaAndTableName());

            File InputFile = transferSets.get(i).getInputSqlFile();
            /**try {
                ScriptRunner runner = new ScriptRunner((Connection) sourceDb, false, false);
                String file = "~/path/to/script.sql";
                runner.runScript(new Reader(new FileReader(file)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }**/

            if (InputFile.canRead() == true) {
                Path FilePath = Paths.get(InputFile.getAbsolutePath());
                try {
                    //Read SQL-File
                    byte[] encodedFile = Files.readAllBytes(FilePath);
                    String Query = new String(encodedFile, "UTF-8");
                    Db2DbLog.log(2, Query);

                    //Check if there are no bad words in the Statement
                    if (containsAKeyword(Query, keywords) == true) {
                        Db2DbLog.log(1, "FOUND NOT ALLOWED WORDS IN SQL STATEMENT!");
                        throw new SQLException(e);
                    }

                    //Run Query
                    sourceDb.setAutoCommit(false);
                    Statement SQLStatement = sourceDb.createStatement();
                    ResultSet rs = null;
                    try {
                        rs = SQLStatement.executeQuery(Query);
                        sourceDb.commit();
                    } catch (SQLException f) {
                        Db2DbLog.log(1, String.valueOf(f));
                    }

                    //UND HIER WIRD DANN DAS RESULTSET IN DIE OUTPUT-DB GESCHRIEBEN!
                    while (rs.next()) {
                        System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3));
                    }


                } catch (Exception g){
                    Db2DbLog.log(1, String.valueOf(g));
                }

            }
            else {
                Db2DbLog.log(1, "Could not read SQL-file!");
                throw new IOException(e);
            }



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
