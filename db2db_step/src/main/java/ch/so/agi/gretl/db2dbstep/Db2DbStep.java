package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnectorImp;

import java.util.List;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public interface Db2DbStep {
    public void execute(DbConnectorImp sourceDb, DbConnectorImp targetDb, List<TransferSet> transferSets);
}