package ch.so.agi.gretl.sqlexecutorstep;

import java.io.File;
import java.sql.Connection;


/**
 * Created by barpastu on 09.05.17.
 */
public interface SQLExecutor {
    public void execute(Connection Db, File[] SQLFiles);

    public boolean checkFiles(File[] SQLFiles);

    public boolean readableFiles(File[] SQLFiles);
}
