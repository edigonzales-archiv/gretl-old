package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnectorImp;

import java.util.List;

/**
 * Created by bjsvwsch on 03.05.17.
 */
public class Db2DbStepImpl
    implements Db2DbStep {

    /** KONSTRUKTOR **/

    /** HAUPTFUNKTION **/

    @Override
    public void execute(DbConnectorImp sourceDb, DbConnectorImp targetDb, List<TransferSet> transferSets) {

        for (int i = 0; i < transferSets.size(); i++) {
            System.out.println(transferSets.get(i));
        }

    }
}
