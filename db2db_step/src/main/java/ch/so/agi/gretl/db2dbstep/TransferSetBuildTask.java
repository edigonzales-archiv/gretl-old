package ch.so.agi.gretl.db2dbstep;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjsvwsch on 19.05.17.
 */
public class TransferSetBuildTask extends DefaultTask {
    @TaskAction
    public List<TransferSet> db2DbStepTask() {
        ArrayList<TransferSet> mylist = new ArrayList<TransferSet>();
       return mylist;
    };

}
