package ch.so.agi.gretl.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

/**
 * Created by bjsvwsch on 09.05.17.
 */
public class LoggerImp implements Logger {

    private static final String INFO_NAME = "INFO: ";
    private static final String DEBUG_NAME = "DEBUG: ";

    private static Logger instance = null;
    private LoggerImp() {}

    public static Logger getInstance()

    {
        if(instance == null) {
            instance = new LoggerImp();
        }
        return instance;
    }

    @Override
    public void log(Integer LogLevel, String Message) {
        if (LogLevel==Logger.INFO_LEVEL) {
            /** INFO **/
            /**Write to a file**/
            System.out.println(INFO_NAME+", "+Message);

        }
        else if (LogLevel==Logger.DEBUG_LEVEL) {
            /** DEBUG **/
            /**Write to another file**/
            System.out.println(DEBUG_NAME+", "+Message);

        }
        else {
            /**UNEXPECTED LOGLEVEL**/
            /**Throws Exception**/
            throw new RuntimeException("Logging-Fehler");
        }


    }
}
