package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnector;
import ch.so.agi.gretl.core.TransactionContext;
import ch.so.agi.gretl.logging.Logger;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bjsvwsch on 19.05.17.
 */
public class Db2DbStepTask extends DefaultTask {

    @Input
    public TransactionContext sourceDb;
    @Input
    public TransactionContext targetDb;
    @Input
    public List<TransferSet> transferSet;
/**
    @Input
    TransactionContext getSourceDb() {return sourceDb;}

    @Input
    TransactionContext getTargetDb() {return targetDb;}

    @Input
    List<TransferSet> getTransferSet() {return transferSet;}
**/
    @TaskAction
    public void db2DbStepTask() {

        try {
            new Db2DbStep(sourceDb.getDbConnection(),targetDb.getDbConnection()).processAllTransferSets(transferSet);
            Logger.log(Logger.INFO_LEVEL,"Task start");
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(Logger.INFO_LEVEL,"SQLException: "+e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.log(Logger.INFO_LEVEL,"FileNotFoundException: "+e.getMessage());
        }


    }
}

