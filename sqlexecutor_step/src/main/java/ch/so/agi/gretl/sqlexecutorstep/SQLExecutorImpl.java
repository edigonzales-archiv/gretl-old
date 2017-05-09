package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.Logger;
import ch.so.agi.gretl.core.LoggerImp;
import sun.text.normalizer.UTF16;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorImpl implements SQLExecutor {


    @Override
    public void execute(Connection Db, File[] SQLFiles) {
        Logger SQLExecuterLog = LoggerImp.getInstance();

        //Check Files for correct file extension
        SQLExecutorImpl sqlExecutorInst = new SQLExecutorImpl();
        Boolean correctFileExtension = sqlExecutorInst.checkFiles(SQLFiles);

        //Check if all Files are readable
        if (correctFileExtension==true){
            Boolean FilesReadable=sqlExecutorInst.readableFiles(SQLFiles);

            if (FilesReadable==true){

                for (int i=0; i<SQLFiles.length; i++) {
                    Path FilePath = Paths.get(SQLFiles[i].getAbsolutePath());

                    try {
                        //Read SQL-File
                        byte[] encodedFile = Files.readAllBytes(FilePath);
                        String Query =new String(encodedFile, "UTF-8");
                        SQLExecuterLog.log(2,Query);

                        //Test Query
                        Db.setAutoCommit(false);
                        Statement SQLStatement = Db.createStatement();

                        try {
                            SQLStatement.execute(  Query );
                            //Db.commit();

                        } catch (SQLException e) {
                            Db.rollback();
                            SQLExecuterLog.log(1,e.toString());
                        }

                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }


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

            } else {
                FileState = false;
                SQLExecuterLog.log(1, "incorrect SQL-File: " + filePath);
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

            } else {
                SQLExecuterLog.log(2, "File can not be read: " + filePath);
                FileState = false;
            }

        }
        return FileState;
    }


}
