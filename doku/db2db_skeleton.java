package ch.so.agi.gretl.db2dbstep;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.util.List;

/**
 * Created by bjsvwjek on 11.05.17.
 */
public class Db2DbSkeleton {

    /**
     * Führt für alle Transfersets die Transfers von der Quell- in die Zieldatenbank
     * durch und schliesst die Transaktion ab.
     * @param srcCon
     * @param targetCon
     * @param transferSet
     */
    private void processAllTransferSets(Connection srcCon, Connection targetCon, List transferSets)
    {
        for(Object transferSet : transferSets){
            processTransferSet(srcCon, targetCon, transferSet);
        }
    }

    /**
     * Führt für das Transferset den Transfer vom Quell-Resultset in die entsprechende Tabelle
     * der Zieldatenbank durch.
     * @param srcCon
     * @param targetCon
     * @param transferSet
     */
    private void processTransferSet(Connection srcCon, Connection targetCon, Object transferSet)
    {
        if("transferSet.deleteDestTableContents()")
            deleteDestTableContents(targetCon, "transferSet.destTableName");

        PreparedStatement insertRowStatement = createInsertRowStatement(
                srcCon,
                "transferSet.getSrcStatement()",
                "transferSet.getDestTableName()");

        ResultSet rs = createResultSet(srcCon, "sqlSelectStatement");

        while(rs.next())
        {
            transferRow(rs, insertRowStatement);
        }
    }

    /**
     * Kopiert eine Zeile des Quell-ResultSet in die Zieltabelle
     * @param rs
     * @param insertRowStatement
     */
    private void transferRow(ResultSet rs, PreparedStatement insertRowStatement) {
    }

    private void deleteDestTableContents(Connection targetCon, String destTableName) {
    }

    /**
     * Erstellt mittels Quell-SelectStatement auf die Quelldatenbank das ResultSet.
     */
    private ResultSet createResultSet(Connection srcCon, String sqlSelectStatement) {
        return (ResultSet)"";
    }

    /**
     * Erstellt aufgrund der Metadaten des Quell-SelectStatements das Statement für den Insert einer
     * Zeile in die Zieltabelle
     * @param srcCon
     * @param transferSet
     * @return
     */
    private PreparedStatement createInsertRowStatement(Connection srcCon, String srcQuery, String destTableName) {
        return (PreparedStatement)"";
    }



}


