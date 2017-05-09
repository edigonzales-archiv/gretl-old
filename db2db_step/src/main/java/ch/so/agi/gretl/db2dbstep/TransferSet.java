package ch.so.agi.gretl.db2dbstep;

import java.io.File;
import java.lang.reflect.Array;

/**
 * Created by bjsvwsch on 04.05.17.
 */
public class TransferSet {


    private Boolean truncate;
    private File inputSqlFile;
    private String outputQualifiedSchemaAndTableName;


    public TransferSet(Boolean truncate, File inputSqlFile, String outputQualifiedSchemaAndTableName){
        this.truncate = truncate;
        this.inputSqlFile = inputSqlFile;
        this.outputQualifiedSchemaAndTableName = outputQualifiedSchemaAndTableName;
    }

    public Boolean getTruncate() {
        return truncate;
    }

    public File getInputSqlFile() {
        return inputSqlFile;
    }

    public String getOutputQualifiedSchemaAndTableName() {
        return outputQualifiedSchemaAndTableName;
    }

}
