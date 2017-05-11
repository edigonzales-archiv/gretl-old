package ch.so.agi.gretl.sqlexecutorstep;

import ch.so.agi.gretl.core.DbConnectorImp;
import org.junit.Test;
import java.io.File;
import java.sql.Connection;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorTest {

    @Test
    public void executeIncorrectFileExtension() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnectorImp dbConn = new DbConnectorImp();
        Connection con = dbConn.Con("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/dummy.txt");
        File file1 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query.sql");
        File file2 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/querasdfasdfy.sql");
        File[] sqlListe ={file, file1, file2};

        x.execute(con,sqlListe);
    }

    @Test
    public void execute() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnectorImp dbConn = new DbConnectorImp();
        Connection con = dbConn.Con("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file1 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query.sql");
        File file2 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query_farben.sql");
        File[] sqlListe ={ file1, file2};

        x.execute(con,sqlListe);


    }

    @Test
    public void executeIncorrectQuery() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnectorImp dbConn = new DbConnectorImp();
        Connection con = dbConn.Con("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file1 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query.sql");
        File file2 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/wrong_query.sql");
        File file3 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query_farben.sql");
        File[] sqlListe ={ file1, file2,file3};

        x.execute(con,sqlListe);

    }


    @Test
    public void executeNoFiles() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnectorImp dbConn = new DbConnectorImp();
        Connection con = dbConn.Con("jdbc:postgresql://10.36.54.198:54321/sogis", "barpastu", null);
        File file1 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query.sql");
        File file2 = new File("/home/barpastu/codebasis/trunk/sqlexecutor_step/src/main/java/query_farben.sql");
        File[] sqlListe = {};

        x.execute(con, sqlListe);
    }

}