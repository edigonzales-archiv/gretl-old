package ch.so.agi.gretl.sqlexecutorstep;

import ch.so.agi.gretl.core.TransactionContext;
import ch.so.agi.gretl.logging.Logger;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bjsvwsch on 19.05.17.
 */
public class SqlExecuterStepTask extends DefaultTask {

    @Input
    public TransactionContext  sourceDb;


    @Input
    public File[]  sqlFiles;


    @TaskAction
    public void sqlExecuterStepTask() {

        try {
            new SQLExecutor().execute(sourceDb.getDbConnection(),sqlFiles);
            Logger.log(Logger.INFO_LEVEL,"Task start");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(Logger.INFO_LEVEL,"Exception: "+e.getMessage());
        }
    }
}

