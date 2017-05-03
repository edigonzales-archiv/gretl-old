package ch.so.agi.gretl;

import java.sql.Connection;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImpl
    implements Db2DbStep {



    @Override
    public boolean execute(Connection quellDb, Connection zielDb, Boolean trunc, String sqlfile, String OutputSchemaAndTable) {
        return false;
    }
}
