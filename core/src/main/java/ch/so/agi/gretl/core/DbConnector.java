package ch.so.agi.gretl.core;

import ch.so.agi.gretl.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bjsvwsch on 02.05.17.
 */
public class DbConnector {
    private static Connection con=null;

    /**
     * Returns the connection to a specific database. The database is specified by the arguments ConnectionUrl,
     * UserName and Password.
     *
     * @param ConnectionUrl database specific JDBC-Connection-URL
     * @param UserName      database user
     * @param Password      password of given database user
     * @return              the connection to the specific database
     */

    public static Connection connect(String ConnectionUrl, String UserName, String Password) {
        try {
            con = DriverManager.getConnection(
                    ConnectionUrl,UserName,Password);
            con.setAutoCommit(false);
            Logger.log(Logger.DEBUG_LEVEL, "DB connected with these Parameters:  ConnectionURL:" + ConnectionUrl
                        + " Username: " + UserName + " Password: " + Password);
        } catch (SQLException e) {
            if (con!=null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException f) {
                    Logger.log(Logger.INFO_LEVEL, f);
                }
            }
            throw new RuntimeException("Could not connect with database: ", e);
        };
        return con;
    }
}
