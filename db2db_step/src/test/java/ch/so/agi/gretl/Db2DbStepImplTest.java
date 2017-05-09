package ch.so.agi.gretl;

import ch.so.agi.gretl.db2dbstep.Db2DbStepImpl;
import ch.so.agi.gretl.db2dbstep.TransferSet;
import ch.so.agi.gretl.dbconnector.DbConnectorImp;
import org.junit.Test;

import java.io.File;
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
                new File("../../../../test/testsqlfile.sql"),
                new String("java.colors")));

        DbConnectorImp x = new DbConnectorImp();
        x.Con("jdbc:postgresql://10.36.54.200:54321/sogis", "bjsvwsch", null);

        DbConnectorImp y = new DbConnectorImp();
        y.Con("jdbc:postgresql://10.36.54.200:54321/sogis", "bjsvwsch", null);

        Db2DbStepImpl db2db = new Db2DbStepImpl();
        db2db.execute(x,y,mylist);
    }

}