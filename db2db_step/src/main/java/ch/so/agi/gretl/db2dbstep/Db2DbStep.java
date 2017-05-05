package ch.so.agi.gretl;

import ch.so.agi.gretl.db2dbstep.TransferSet;
import java.sql.Connection;
import java.util.List;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public interface Db2DbStep {
    public void execute(Connection sourceDb, Connection targetDb, List<TransferSet> transferSets);
}