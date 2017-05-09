package ch.so.agi.gretl.sqlexecutorstep;

import org.junit.Test;

import ch.so.agi.gretl.*;

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
        Connection con = DriverManager.getConnection("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/dummy.txt");
        File[] sqlListe ={file};

        x.execute(con,sqlListe);


    }

}