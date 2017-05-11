package ch.so.agi.gretl.core;

import org.slf4j.LoggerFactory;
/**
 * Created by bjsvwsch on 09.05.17.
 */
public class Logger  {

    private static final String INFO_NAME = "INFO: ";
    private static final String DEBUG_NAME = "DEBUG: ";
    public static final int INFO_LEVEL = 1;
    public static final int DEBUG_LEVEL = 2;

    private Logger() {}


    static public void log(int LogLevel, String message) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
        if (LogLevel== INFO_LEVEL) {
            /** INFO **/
            /**Write to a file**/
            logger.info(message);


        }
        else if (LogLevel== DEBUG_LEVEL) {
            /** DEBUG **/
            /**Write to another file**/
            logger.debug(message);

        }
        else {
            /**UNEXPECTED LOGLEVEL**/
            /**Throws Exception**/
            throw new RuntimeException("Logging-Fehler");
        }


    }

    static public void log(int LogLevel, Exception e) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
        logger.debug("", e);
    }
}
