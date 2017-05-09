package ch.so.agi.gretl.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bjsvwsch on 09.05.17.
 */
public class LoggerImpTest {
    @Test
    public void getInstance() throws Exception {
    }

    @Test
    public void log() throws Exception {
        Logger log1 = LoggerImp.getInstance();
        log1.log(Logger.INFO_LEVEL,"Super Log");
        log1.log(Logger.DEBUG_LEVEL,"Super Log2");
    }

}