package ch.so.agi.gretl;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.EmptyFileException;
import ch.so.agi.gretl.core.NotAllowedExpressionException;
import ch.so.agi.gretl.db2dbstep.Db2DbStep;
import ch.so.agi.gretl.db2dbstep.TransferSet;
import ch.so.agi.gretl.logging.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImplTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private String e;


    @Test
    public void Db2DbPositiveTest() throws Exception {
        //unittest
        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);
        con.setAutoCommit(true);
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE colors ( " +
                "  rot integer, " +
                "  gruen integer, " +
                "  blau integer, " +
                "  farbname VARCHAR(200))");
        stmt.execute("INSERT INTO colors  VALUES (255,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (251,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (0,0,255,'blau')");
        stmt.execute("CREATE TABLE colors_copy (rot integer, gruen integer, blau integer, farbname VARCHAR(200))");

        File sqlFile =  folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write("SELECT * FROM colors");
        writer.close();


        ArrayList<TransferSet> mylist = new ArrayList<TransferSet>();
        mylist.add(new TransferSet(
                new Boolean(true),
                new File(sqlFile.getAbsolutePath().toString()),
                new String("colors_copy")
        ));


        DbConnector x = new DbConnector();
        Connection xcon = x.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        DbConnector y = new DbConnector();
        Connection ycon = y.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        Db2DbStep db2db = new Db2DbStep(xcon, ycon);
        db2db.processAllTransferSets(mylist);
        xcon.close();
        ycon.close();

        ResultSet rs = stmt.executeQuery("SELECT * FROM colors_copy WHERE farbname = 'blau'");
        while(rs.next()) {
            if (!rs.getObject("rot").equals(0)) throw new Exception(e);
            if (!rs.getObject("farbname").equals("blau")) throw new Exception(e);
        }
        stmt.execute("DROP TABLE colors");
        stmt.execute("DROP TABLE colors_copy");
        con.close();
    }

    @Test
    public void Db2DbNotAllowedTest() throws Exception {
        //unittest
        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);
        con.setAutoCommit(true);
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE colors ( " +
                "  rot integer, " +
                "  gruen integer, " +
                "  blau integer, " +
                "  farbname VARCHAR(200))");
        stmt.execute("INSERT INTO colors  VALUES (255,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (251,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (0,0,255,'blau')");

        File sqlFile = folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write("DELETE FROM colors");
        writer.close();


        ArrayList<TransferSet> mylist = new ArrayList<TransferSet>();
        mylist.add(new TransferSet(
                new Boolean(false),
                new File(sqlFile.getAbsolutePath().toString()),
                new String("colors_copy")
        ));


        DbConnector x = new DbConnector();
        Connection xcon = x.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        DbConnector y = new DbConnector();
        Connection ycon = y.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        Db2DbStep db2db = new Db2DbStep(xcon, ycon);

        try {
            db2db.processAllTransferSets(mylist);
            Assert.fail();
        } catch (NotAllowedExpressionException e) {

        } finally {
            xcon.close();
            ycon.close();
            stmt.execute("DROP TABLE colors");
            con.close();
        }

    }

    @Test
    public void Db2DbEmptyFileTest() throws Exception {
        //unittest
        DbConnector dbConn = new DbConnector();
        Connection con = dbConn.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);
        con.setAutoCommit(true);
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE colors ( " +
                "  rot integer, " +
                "  gruen integer, " +
                "  blau integer, " +
                "  farbname VARCHAR(200))");
        stmt.execute("INSERT INTO colors  VALUES (255,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (251,0,0,'rot')");
        stmt.execute("INSERT INTO colors  VALUES (0,0,255,'blau')");

        File sqlFile = folder.newFile("query.sql");

        ArrayList<TransferSet> mylist = new ArrayList<TransferSet>();
        mylist.add(new TransferSet(
                new Boolean(false),
                new File(sqlFile.getAbsolutePath().toString()),
                new String("colors_copy")
        ));


        DbConnector x = new DbConnector();
        Connection xcon = x.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        DbConnector y = new DbConnector();
        Connection ycon = y.Con("jdbc:derby:memory:myInMemDB;create=true", "bjsvwsch", null);

        Db2DbStep db2db = new Db2DbStep(xcon, ycon);

        try {
            db2db.processAllTransferSets(mylist);
            Assert.fail("EmptyFileException müsste geworfen werden");
        } catch (EmptyFileException e) {

        } finally {
            xcon.close();
            ycon.close();
            stmt.execute("DROP TABLE colors");
            con.close();
        }

    }

}