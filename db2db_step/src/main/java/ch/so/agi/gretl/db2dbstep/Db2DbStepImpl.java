package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.Db2DbStep;
import ch.so.agi.gretl.dbconnector.DbConnectorImp;
import ch.so.agi.gretl.dbconnector.DbConnector;

import java.sql.Connection;
import java.util.List;

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
    public void execute(Connection sourceDb, Connection targetDb, List<TransferSet> transferSets) {

    }
}
