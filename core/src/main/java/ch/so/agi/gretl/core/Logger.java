package ch.so.agi.gretl.core;

/**
 * Created by bjsvwsch on 09.05.17.
 */
public class Logger  {

    private static final String INFO_NAME = "INFO: ";
    private static final String DEBUG_NAME = "DEBUG: ";
    public static final int INFO_LEVEL = 1;
    public static final int DEBUG_LEVEL = 2;

    private Logger() {}


    static public void log(int LogLevel, String Message) {
        if (LogLevel== INFO_LEVEL) {
            /** INFO **/
            /**Write to a file**/
            System.out.println(INFO_NAME+" "+Message);

        }
        else if (LogLevel== DEBUG_LEVEL) {
            /** DEBUG **/
            /**Write to another file**/
            System.out.println(DEBUG_NAME+" "+Message);

        }
        else {
            /**UNEXPECTED LOGLEVEL**/
            /**Throws Exception**/
            throw new RuntimeException("Logging-Fehler");
        }


    }

    static public void log(int LogLevel, Exception e) {
        log(LogLevel, e.getMessage());
    }
}
