package ch.so.agi.gretl.core;

import org.junit.Test;

/**
 * Created by bjsvwsch on 09.05.17.
 */
public class LoggerTest {
    @Test
    public void getInstance() throws Exception {
    }

    @Test
    public void log() throws Exception {
        Logger.log(Logger.INFO_LEVEL,"Super Log");
        Logger.log(Logger.DEBUG_LEVEL,"Super Log2");
    }

}