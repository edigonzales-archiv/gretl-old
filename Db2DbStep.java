package ch.so.agi.ms;

import java.sql.Connection;

/**
 * Created by bjsvwsch on 26.04.17.
 */
public interface Db2DbStep {
    public boolean execute(Connection quellDb, Connection zielDb, Boolean trunc, String file, String Output);
}
