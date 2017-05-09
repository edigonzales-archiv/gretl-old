package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.Logger;
import ch.so.agi.gretl.core.LoggerImp;



/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorImpl implements SQLExecutor {


    @Override
    public void execute(Connection Db, File[] SQLFiles) {
        Logger SQLExecuterLog = LoggerImp.getInstance();

        //Check Files for correct file extension
        SQLExecutorImpl sqlExecutorInst = new SQLExecutorImpl();
        Boolean FileState = sqlExecutorInst.checkFiles(SQLFiles);



        if (FileState!=false){
            SQLExecuterLog.log(2,"all Files have the extension .sql");
            for (int i=0; i<SQLFiles.length; i++) {
               if (SQLFiles[1].canRead() == true){

              }
            }


            System.out.println("Huhu");
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


}
