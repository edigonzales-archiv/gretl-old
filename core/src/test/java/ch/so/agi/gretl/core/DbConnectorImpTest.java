package ch.so.agi.gretl.core;

import org.junit.Test;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class DbConnectorImpTest {
    @Test
    public void con_pgsql() throws Exception {
        DbConnectorImp x = new DbConnectorImp();
        x.Con("jdbc:postgresql://10.36.54.200:54321/sogis", "bjsvwsch", null);
    }

    @Test
    public void con_oracle() throws Exception {
        DbConnectorImp x = new DbConnectorImp();
        x.Con("jdbc:oracle:thin:@//srsofaioi18632.verw.rootso.org:1521/capi_p","SRVAGIGB", "");
    }

    @Test
    public void con_mssql() throws Exception {
        DbConnectorImp x = new DbConnectorImp();
        x.Con("jdbc:sqlserver://srsofaioi18943.verw.rootso.org:1433", "imdaspro_gis", "");
    }

    @Test
    public void con_sqlite() throws Exception {
        DbConnectorImp x = new DbConnectorImp();
        x.Con("jdbc:sqlite:./test/gemeindegrenzen.sqlite", null, null);
    }
}