package ch.so.agi.gretl.db2dbstep;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by bjsvwsch on 19.05.17.
 */
public class TransferSetAddTask {

    private boolean delete;

    private File file;

    private String tableName;

    private List<TransferSet> myList;

    public List<TransferSet> add(){
        myList.add(new TransferSet(
                delete,
                file,
                tableName
                )

        );
        return myList;
    }
}
