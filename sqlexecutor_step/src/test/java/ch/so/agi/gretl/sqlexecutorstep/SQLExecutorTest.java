package ch.so.agi.gretl.sqlexecutorstep;

import ch.so.agi.gretl.core.DbConnector;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;


/**
 * Created by barpastu on 09.05.17.
 */
public class SQLExecutorTest {

    //Create temporary folder for saving sqlfiles
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Test
    public void executeWithoutFiles() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "barpastu", null);
        File[] sqlListe ={};

        try {
            x.execute(con,sqlListe);
            Assert.fail();
        } catch (Exception e) {

        }
    }

    @Test
    public void executeWithoutDb() throws Exception {
        SQLExecutor x = new SQLExecutor();
        Connection con = null;

        File sqlFile =  folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write(" SELECT \n" +
                "  ST_Collect(geometrie),\n" +
                "  forstreviere_forstkreis.aname\n" +
                "FROM \n" +
                "  awjf_forstreviere.forstreviere_forstreviergeometrie\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstrevier\n" +
                "    ON forstreviere_forstrevier.t_id=forstreviere_forstreviergeometrie.forstrevier\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstkreis\n" +
                "    ON forstreviere_forstkreis.t_id=forstreviere_forstrevier.forstkreis\n" +
                "GROUP BY forstreviere_forstkreis.t_id");
        writer.close();
        File[] sqlListe ={sqlFile};

        try {
            x.execute(con,sqlListe);
            Assert.fail();
        } catch (Exception e) {

        }
    }


    @Test
    public void executeDifferentExtensions() throws Exception {
        SQLExecutor x = new SQLExecutor();

        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "barpastu", null);

        File sqlFile =  folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write(" SELECT \n" +
                "  ST_Collect(geometrie),\n" +
                "  forstreviere_forstkreis.aname\n" +
                "FROM \n" +
                "  awjf_forstreviere.forstreviere_forstreviergeometrie\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstrevier\n" +
                "    ON forstreviere_forstrevier.t_id=forstreviere_forstreviergeometrie.forstrevier\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstkreis\n" +
                "    ON forstreviere_forstkreis.t_id=forstreviere_forstrevier.forstkreis\n" +
                "GROUP BY forstreviere_forstkreis.t_id");
        writer.close();

        File sqlFile1 =  folder.newFile("query1.txt");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(sqlFile1));
        writer1.write(" SELECT \n" +
                "  ST_Collect(geometrie),\n" +
                "  forstreviere_forstkreis.aname\n" +
                "FROM \n" +
                "  awjf_forstreviere.forstreviere_forstreviergeometrie\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstrevier\n" +
                "    ON forstreviere_forstrevier.t_id=forstreviere_forstreviergeometrie.forstrevier\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstkreis\n" +
                "    ON forstreviere_forstkreis.t_id=forstreviere_forstrevier.forstkreis\n" +
                "GROUP BY forstreviere_forstkreis.t_id");
        writer1.close();
        File[] sqlListe ={sqlFile, sqlFile1};

        try {
            x.execute(con,sqlListe);
            Assert.fail();
        } catch (Exception e) {

        }
    }


    @Test
    public void executeEmptyFile() throws Exception {
        SQLExecutor x = new SQLExecutor();
        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "barpastu", null);
        con.setAutoCommit(true);
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE colors ( " +
                "  rot integer, " +
                "  gruen integer, " +
                "  blau integer, " +
                "  farbname VARCHAR(200))");
        stmt.execute("INSERT INTO colors  VALUES (255,0,0,'rot')");


        File sqlFile =  folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write(" SELECT \n" +
                "  colors.rot,\n" +
                "  gruen,\n" +
                "  colors.blau,\n" +
                "  farbname\n" +
                "FROM colors\n" +
                "WHERE farbname = 'rot'");
        writer.close();

        File sqlFile1 =  folder.newFile("query1.sql");
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(sqlFile1));
        writer1.write("");
        writer1.close();
        File[] sqlListe ={sqlFile, sqlFile1};

        x.execute(con,sqlListe);
    }

}