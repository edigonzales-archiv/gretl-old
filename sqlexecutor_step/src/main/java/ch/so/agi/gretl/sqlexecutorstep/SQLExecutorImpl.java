package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorImpl implements SQLExecutor {



    @Override
    public boolean execute(Connection Db, File[] SQLFiles){
        for (int i=0; i<SQLFiles.length; i++){

            System.out.println(SQLFiles[i]);
        }
        return true;
    }
}
