package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import ch.so.agi.gretl.core.Logger;
import ch.so.agi.gretl.core.LoggerImp;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorImpl implements SQLExecutor {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");




    @Override
    public void execute(Connection Db, File[] SQLFiles) {

        Logger SQLExecuterLog = LoggerImp.getInstance();
        String CompleteQuery = "";

        Calendar cal = Calendar.getInstance();

        SQLExecuterLog.log(1,"Start SQLExecutor: " + dateFormat.format(cal.getTime()));



        //Check Files for correct file extension
        SQLExecutorImpl sqlExecutorInst = new SQLExecutorImpl();
        Boolean correctFileExtension = sqlExecutorInst.checkFiles(SQLFiles);

        //Check if all Files are readable
        if (correctFileExtension==true){
            Boolean FilesReadable=sqlExecutorInst.readableFiles(SQLFiles);
            SQLExecuterLog.log(2, "All files do have the correct extension");

            if (FilesReadable==true){
                SQLExecuterLog.log(2,"All files are readable.");

                for (int i=0; i<SQLFiles.length; i++) {
                    Path FilePath = Paths.get(SQLFiles[i].getAbsolutePath());

                    try {
                        //Read SQL-File
                        byte[] encodedFile = Files.readAllBytes(FilePath);
                        String Query =new String(encodedFile, "UTF-8").trim();
                        SQLExecuterLog.log(2,"Query number " + (i+1) + " : " + Query);

                        //Test query and put all queries together to one query
                        Db.setAutoCommit(false);
                        Statement SQLStatement = Db.createStatement();

                        try {
                            SQLStatement.execute(  Query );
                            Db.rollback();

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
                            Db.rollback();
                            Db.close();

                            SQLExecuterLog.log(1,e.toString());
                            SQLExecuterLog.log(1, "DB-Connection closed");
                            SQLExecuterLog.log(2, "SQLExecutor canceled!");
                        }

                        SQLExecuterLog.log(2,"Complete query: " +CompleteQuery);


                    } catch (Exception e){
                        SQLExecuterLog.log(1, "Could not read file: " +e.toString());

                        try {
                            Db.close();
                            SQLExecuterLog.log(2, "DB-Connection closed.");
                        } catch (SQLException sqlE){
                            SQLExecuterLog.log (2, "Could not close DB-Connection: " + sqlE.toString());
                        } finally {
                            SQLExecuterLog.log(2, "SQLExecutor canceled!");
                        }

                    }
                }
                //Test complete query
                try{
                    Statement SQLStatement = Db.createStatement();
                    Db.setAutoCommit(false);

                    try {
                        SQLStatement.execute(CompleteQuery);
                        Db.commit();

                    } catch (SQLException e) {
                        SQLExecuterLog.log(1, "Faulty complete query: " + e.toString());
                        SQLExecuterLog.log(2, "SQLExecutor canceled!");
                    }

                } catch (SQLException e){
                    SQLExecuterLog.log(1, "Could not create Statement");
                    SQLExecuterLog.log(2, "SQLExecutor canceled!");
                }
            }
        }
        if (Db != null) {
            try {
                Db.close();
                Db = null;
                SQLExecuterLog.log(1, "DB-Connection closed.");
            } catch (SQLException e) {
                SQLExecuterLog.log(1, "DB-Connection not closed: " + e.toString());
                SQLExecuterLog.log(2, "SQLExecutor canceled!");
            }
        }
        SQLExecuterLog.log(1,"End SQLExecutor: " + dateFormat.format(cal.getTime()));
    }

    @Override
    public boolean checkFiles(File[] SQLFiles){
        Logger SQLExecuterLog = LoggerImp.getInstance();
        Boolean FileState = true;

        //Check if Files are .sql-Files
        for (int i=0; i<SQLFiles.length; i++) {
            //Getting Filextension (e.g. ".sql")
            String filePath = SQLFiles[i].getAbsolutePath();
            String filename = filePath.substring(filePath.lastIndexOf("/"));
            String FileExtension = filename.substring(filename.indexOf("."));

            SQLExecuterLog.log(2, "Filepath: " + filePath);

            //Check for wrong Fileextensions (everything else than .sql)
            if (FileExtension.equals(".sql")) {
                SQLExecuterLog.log(2, "Correct SQL-File: " + filePath);

            } else {
                FileState = false;
                SQLExecuterLog.log(1, "Incorrect SQL-File: " + filePath);
                SQLExecuterLog.log(2, "SQLExecutor canceled!");
                System.exit(1);
            }
        }
        return FileState;

    }

    @Override
    public boolean readableFiles(File[] SQLFiles) {
        Logger SQLExecuterLog = LoggerImp.getInstance();
        Boolean FileState = true;

        //Check if Files are readable
        for (int i=0; i<SQLFiles.length; i++) {
            String filePath = SQLFiles[i].getAbsolutePath();

            if (SQLFiles[i].canRead() == true){
                SQLExecuterLog.log(2,"File readable: " + filePath);
            } else {
                SQLExecuterLog.log(1, "File can not be read: " + filePath);
                SQLExecuterLog.log(2, "SQLExecutor canceled!");
                FileState = false;
                System.exit(1);
            }

        }
        return FileState;
    }


}
