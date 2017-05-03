package ch.so.agi.gretl.dbconnector;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class DbConnectorImpTest {
    @Test
    public void con() throws Exception {
        DbConnectorImp x=new DbConnectorImp();
        x.Con("jdbc:postgresql://10.36.54.200:54321/sogis","bjsvwsch", null);
    }

}