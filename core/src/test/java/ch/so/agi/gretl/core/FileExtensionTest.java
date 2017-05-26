package ch.so.agi.gretl.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by barpastu on 26.05.17.
 */
public class FileExtensionTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void getFileExtension() throws Exception {
        File sqlFile =  folder.newFile("query.sql");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write(" SELECT \n" +
                "  ST_Collect(geometrie),\n" +
                "  forstreviere_forstkreis.aname\n" +
                "FROM \n" +
                "  awjf_forstreviere.forstreviere_forstreviergeometrie\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstrevier\n" +
                "    ON forstreviere_forstrevier.t_id=forstreviere_forstreviergeometrie.forstrevier\n" +
                "  LEFT JOIN awjf_forstreviere.forstreviere_forstkreis\n" +
                "    ON forstreviere_forstkreis.t_id=forstreviere_forstrevier.forstkreis\n" +
                "GROUP BY forstreviere_forstkreis.t_id");
        writer.close();
        if(FileExtension.getFileExtension(sqlFile).equals("sql")){

        }
    }

}