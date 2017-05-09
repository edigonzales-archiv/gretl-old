package ch.so.agi.gretl.sqlexecutorstep;

import ch.so.agi.gretl.core.DbConnectorImp;
import org.junit.Test;

import ch.so.agi.gretl.core.DbConnector;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorImplTest {
    @Test
    public void execute() throws Exception {
        SQLExecutorImpl x = new SQLExecutorImpl();
        DbConnectorImp dbConn = new DbConnectorImp();
        Connection con = dbConn.Con("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/dummy.txt");
        File file1 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query.sql");
        File file2 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/querasdfasdfy.sql");
        File[] sqlListe ={file, file1, file2};

        x.execute(con,sqlListe);


    }

}