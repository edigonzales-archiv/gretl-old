package ch.so.agi.gretl.db2dbstep;

import java.io.File;
import java.lang.reflect.Array;

/**
 * Created by bjsvwsch on 04.05.17.
 */
public class TransferSet {


    private boolean deleteAllRows;
    private File inputSqlFile;
    private String outputQualifiedSchemaAndTableName;


    public TransferSet(boolean deleteAllRows, File inputSqlFile, String outputQualifiedSchemaAndTableName){
        this.deleteAllRows = deleteAllRows;
        this.inputSqlFile = inputSqlFile;
        this.outputQualifiedSchemaAndTableName = outputQualifiedSchemaAndTableName;
    }

    public boolean getDeleteAllRows() {
        return deleteAllRows;
    }

    public File getInputSqlFile() {
        return inputSqlFile;
    }

    public String getOutputQualifiedSchemaAndTableName() {
        return outputQualifiedSchemaAndTableName;
    }

}
