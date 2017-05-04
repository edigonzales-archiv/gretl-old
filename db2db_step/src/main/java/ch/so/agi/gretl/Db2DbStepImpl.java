package ch.so.agi.gretl;

import ch.so.agi.gretl.dbconnector.DbConnectorImp;
import ch.so.agi.gretl.dbconnector.DbConnector;

import java.sql.Connection;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImpl
    implements Db2DbStep {

    /** KONSTRUKTOR **/
    public void Db2DbStepImpl() {
        DbConnector inputconnection=new DbConnectorImp();
        inputconnection.Con("URL","USER","PW");
    }

    /** HAUPTFUNKTION **/
    @Override
    public void execute(Connection quellDb, Connection zielDb, Boolean trunc, String sqlfile, String OutputSchemaAndTable) {

    }
}
