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
        System.out.println(LogLevel+", "+Message);
        if (LogLevel==1) {
            /** INFO **/
            /**Write to a file**/

        }
        else if (LogLevel==2) {
            /** DEBUG **/
            /**Write to another file**/

        }
        else {
            /**UNEXPECTED LOGLEVEL**/
            /**Throws Exception**/

        }


    }
}
