package ch.so.agi.gretl.core;

/**
 * Created by bjsvwsch on 09.05.17.
 */
public interface Logger {
    public static final int INFO_LEVEL = 1;
    public static final int DEBUG_LEVEL = 2;
    void log(Integer LogLevel, String Message);
}
