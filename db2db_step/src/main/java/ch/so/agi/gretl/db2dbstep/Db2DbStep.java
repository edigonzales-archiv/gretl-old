package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.DbConnectorImp;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by bjsvwsch on 03.05.17.
 */
/**public interface Db2DbStep {
    public void execute(DbConnectorImp sourceDb, DbConnectorImp targetDb, List<TransferSet> transferSets);
}**/

public interface Db2DbStep {
    public void execute(Connection sourceDb, Connection targetDb, List<TransferSet> transferSets) throws IOException;
}