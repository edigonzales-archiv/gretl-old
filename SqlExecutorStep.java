package ch.ehi.so.steps.day1;

import java.nio.file.Files;
import java.sql.Connection;

/**
 * Created by barpastu on 26.04.17.
 */
public interface SqlExecutorStep {
    public boolean execute(Connection Db, Files QueryFiles);
}
