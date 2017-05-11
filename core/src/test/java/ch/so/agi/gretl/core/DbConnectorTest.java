package ch.so.agi.gretl.core;

import org.junit.Test;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class DbConnectorTest {

    @Test
    public void con_derby() throws Exception {
        DbConnector x = new DbConnector();
        x.Con("jdbc:derby:memory:myInMemDB;create=true", null, null);
    }
}