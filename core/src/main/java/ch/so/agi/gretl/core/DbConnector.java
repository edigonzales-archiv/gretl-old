package ch.so.agi.gretl.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bjsvwsch on 02.05.17.
 */
public class DbConnector {
    private Connection con=null;



     public Connection Con(String ConnectionUrl, String UserName, String Password) {
        try {
            con = DriverManager.getConnection(
                    ConnectionUrl,UserName,Password);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e);
            if (con!=null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException f) {
                    System.out.println(f);
                }
            }
            throw new RuntimeException("Super Fehler", e);
        };
        return con;
    }
}
