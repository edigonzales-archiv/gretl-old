package ch.so.agi.gretl.core;

import java.sql.Connection;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public interface DbConnector {
    public Connection Con (String ConnectionUrl, String UserName, String Password);
}
