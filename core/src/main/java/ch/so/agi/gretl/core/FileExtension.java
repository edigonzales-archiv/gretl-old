package ch.so.agi.gretl.core;

import ch.so.agi.gretl.logging.Logger;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.Arrays;

/**
 * Created by barpastu on 26.05.17.
 */
public class FileExtension {


    /**
     * Gets the extension of the given file.
     *
     * @param inputFile File, which should be checked for the extension
     * @return          file extension (e.g. ".sql")
     */
    public static String getFileExtension(File inputFile){
        String filePath =inputFile.getAbsolutePath();
        String[] splittedFilePath = filePath.split("\\.");
        Integer arrayLength=splittedFilePath.length;
        String FileExtension= splittedFilePath[arrayLength-1];

        return FileExtension;
    }
}



