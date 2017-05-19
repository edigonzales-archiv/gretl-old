package ch.so.agi.gretl.sqlexecutorstep;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ch.so.agi.gretl.core.SqlReader;
import ch.so.agi.gretl.logging.Logger;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutor {


    /**
     * Executes the queries within the .sql-files in the specified database. But does not commit SQL-Statements
     *
     * @param db            Database connection
     * @param sqlfiles      Files with .sql-extension which contain queries
     * @throws Exception
     */
    public void execute(Connection db, File[] sqlfiles) throws Exception {

        String CompleteQuery = "";

        Logger.log(Logger.INFO_LEVEL,"Start SQLExecutor");

        //Check for files in list
        if (sqlfiles.length<1){
            throw new Exception("Missing input files");
        }

        //Log all input files
        for (File inputfile: sqlfiles){
            Logger.log(Logger.INFO_LEVEL, inputfile.getAbsolutePath());
        }

        //Check for db-connection
        if (db==null){
            throw new Exception("Missing database connection");
        }


        //Check Files for correct file extension
        for (File file: sqlfiles) {
            String fileExtension = getFileExtension(file);
            if (!fileExtension.equals(".sql")){
                throw new Exception("incorrect file extension at file: " + file.getAbsolutePath());
            }
        }

        // Read Files
        for (File sqlfile: sqlfiles){

            try {
                FileInputStream sqlFileInputStream = new FileInputStream(sqlfile);
                InputStreamReader sqlFileReader = null;

                try {
                    sqlFileReader = new InputStreamReader(sqlFileInputStream);

                    try {
                        executeSqlScript(db, sqlFileReader);
                    } catch (Exception f) {
                        throw new Exception("Could not read file: " + sqlfile.getAbsolutePath() + " " + f.toString());
                    }

                } catch (Exception e) {

                    throw new Exception("Error while reading file:" + sqlfile.getAbsolutePath() + " " + e.toString());
                } finally {

                    try {
                        sqlFileReader.close();

                    } catch (Exception g) {
                        throw new Exception("Could not close Reader");

                    }
                }
            } catch (Exception h){
                throw new Exception("Could not create FileInputStream for file: " + sqlfile.getAbsolutePath() + " " + h.toString());
            }
        }
    }

    /**
     * Gets the extension of the given file.
     *
     * @param inputFile File, which should be checked for the extension
     * @return          file extension (e.g. ".sql")
     */
    private String getFileExtension(File inputFile){
        String filePath =inputFile.getAbsolutePath();
        String filename = filePath.substring(filePath.lastIndexOf("/"));
        String FileExtension = filename.substring(filename.indexOf("."));

        return FileExtension;
    }



    /**
     * Gets the sqlqueries out of the given file and executes the statements on the given database
     * @param conn              Database connection
     * @param inputStreamReader inputStream of a specific file
     */
    private static void executeSqlScript(Connection conn, java.io.InputStreamReader inputStreamReader) {
        java.io.PushbackReader reader = null;
        reader = new java.io.PushbackReader(inputStreamReader);
        try {
            String line = SqlReader.readSqlStmt(reader);
            while (line != null) {
                // exec sql
                line = line.trim();
                if (line.length() > 0) {
                    Statement dbstmt = null;
                    try {
                        try {
                            dbstmt = conn.createStatement();
                            Logger.log(Logger.DEBUG_LEVEL, line);
                            dbstmt.execute(line);
                        } finally {
                            dbstmt.close();
                        }
                    } catch (SQLException ex) {
                        throw new IllegalStateException(ex);
                    }

                }
                // read next line
                line = SqlReader.readSqlStmt(reader);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }





}
