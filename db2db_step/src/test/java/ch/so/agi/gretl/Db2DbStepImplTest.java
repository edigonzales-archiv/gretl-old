package ch.so.agi.gretl;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.db2dbstep.Db2DbStep;
import ch.so.agi.gretl.db2dbstep.TransferSet;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImplTest {
    @Test
    public void execute() throws Exception {
        //unittest
        ArrayList<TransferSet> mylist = new ArrayList<TransferSet>();

        mylist.add(new TransferSet(
                new Boolean(true),
                new File("/home/bjsvwsch/codebasis_test/sql_test.sql"),
                new String("public.geo_gemeinden")));

        DbConnector x = new DbConnector();
        Connection xcon = x.Con("jdbc:postgresql://geodb-t.verw.rootso.org/sogis", "bjsvwsch", null);

        DbConnector y = new DbConnector();
        Connection ycon = y.Con("jdbc:postgresql://10.36.54.200:54321/sogis", "bjsvwsch", null);

        Db2DbStep db2db = new Db2DbStep(xcon, ycon);
        db2db.processAllTransferSets(mylist);
        xcon.close();
        ycon.close();
    }

}