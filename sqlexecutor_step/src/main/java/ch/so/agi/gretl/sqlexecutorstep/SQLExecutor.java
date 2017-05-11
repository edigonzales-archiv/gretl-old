package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ch.so.agi.gretl.core.Logger;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutor {






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
        SQLExecutor sqlExecutorInst = new SQLExecutor();
        Boolean correctFileExtension = sqlExecutorInst.checkFiles(sqlfiles);

        if (!correctFileExtension){
            throw new Exception("incorrect file extension");
        }

        //Check if all Files are readable
        if (correctFileExtension==true){
            Boolean FilesReadable=sqlExecutorInst.readableFiles(sqlfiles);
            Logger.log(2, "All files do have the correct extension");

            if (FilesReadable==true){
                Logger.log(2,"All files are readable.");

                for (int i=0; i<sqlfiles.length; i++) {
                    Path FilePath = Paths.get(sqlfiles[i].getAbsolutePath());

                    try {
                        //Read SQL-File
                        byte[] encodedFile = Files.readAllBytes(FilePath);
                        String Query =new String(encodedFile, "UTF-8").trim();
                        Logger.log(2,"Query number " + (i+1) + " : " + Query);

                        //Test query and put all queries together to one query
                        Statement SQLStatement = db.createStatement();

                        try {
                            SQLStatement.execute(  Query );
                            db.rollback();

                            Logger.log(2,"Query works.");

                            String lastChar = Query.substring(Query.length()-1);

                            if (lastChar.equals(";")) {
                                CompleteQuery = CompleteQuery + Query;
                            } else {
                                if (CompleteQuery.equals("")){
                                    CompleteQuery=Query;
                                } else {
                                    CompleteQuery = CompleteQuery + " ; " + Query;
                                }
                            }

                        } catch (SQLException e) {


                            Logger.log(1,e.toString());
                            Logger.log(1, "DB-Connection closed");
                            Logger.log(2, "SQLExecutor canceled!");
                        }

                        Logger.log(2,"Complete query: " +CompleteQuery);


                    } catch (Exception e){
                        Logger.log(1, "Could not read file: " +e.toString());

                        try {
                            db.close();
                            Logger.log(2, "DB-Connection closed.");
                        } catch (SQLException sqlE){
                            Logger.log (2, "Could not close DB-Connection: " + sqlE.toString());
                        } finally {
                            Logger.log(2, "SQLExecutor canceled!");
                        }

                    }
                }
                //Test complete query
                try{
                    Statement SQLStatement = db.createStatement();

                    try {
                        SQLStatement.execute(CompleteQuery);

                    } catch (SQLException e) {
                        Logger.log(1, "Faulty complete query: " + e.toString());
                        Logger.log(2, "SQLExecutor canceled!");
                    }

                } catch (SQLException e){
                    Logger.log(1, "Could not create Statement");
                    Logger.log(2, "SQLExecutor canceled!");
                }
            } else {
                Logger.log(2, "Something went wrong.");
            }
        } else {
            Logger.log(2, "Something went wrong.");
        }
        if (db != null) {
            try {
                db.close();
                db = null;
                Logger.log(1, "DB-Connection closed.");
            } catch (SQLException e) {
                Logger.log(1, "DB-Connection not closed: " + e.toString());
                Logger.log(2, "SQLExecutor canceled!");
            }
        }
        Logger.log(1,"End SQLExecutor");
    }




    public boolean checkFiles(File[] SQLFiles){
        Boolean FileState = true;

        //Check if Files are .sql-Files
        for (int i=0; i<SQLFiles.length; i++) {
            //Getting Filextension (e.g. ".sql")
            String filePath = SQLFiles[i].getAbsolutePath();
            String filename = filePath.substring(filePath.lastIndexOf("/"));
            String FileExtension = filename.substring(filename.indexOf("."));

            Logger.log(2, "Filepath: " + filePath);

            //Check for wrong Fileextensions (everything else than .sql)
            if (FileExtension.equals(".sql")) {
                Logger.log(2, "Correct SQL-File: " + filePath);

            } else {
                FileState = false;
                Logger.log(1, "Incorrect SQL-File: " + filePath);
                Logger.log(2, "SQLExecutor canceled!");
                System.exit(1);
            }
        }
        return FileState;
    }


    private String getFileExtension(File)





}
