package ch.so.agi.gretl.db2dbstep;

import ch.so.agi.gretl.core.DbConnector;
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
    public String sourceDbUri;

    @Input
    public String targetDbUri;

    @Input
    public List<TransferSet> transferSet;

    @TaskAction
    public void db2DbStepTask() {
        String[] splittedSourceDbUri = sourceDbUri.split(",");
        Connection sourcedb = DbConnector.Con(splittedSourceDbUri[0],splittedSourceDbUri[1],splittedSourceDbUri[2]);

        String[] splittedTargetDbUri = targetDbUri.split(",");
        Connection targetdb = DbConnector.Con(splittedTargetDbUri[0],splittedTargetDbUri[1],splittedTargetDbUri[2]);

        try {
            new Db2DbStep(sourcedb,targetdb).processAllTransferSets(transferSet);
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

