package ch.so.agi.gretl.core;

import org.gradle.api.Project;
import org.gradle.api.ProjectEvaluationListener;
import org.gradle.api.ProjectState;
import org.gradle.api.invocation.Gradle;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by bjsvwsch on 19.05.17.
 */
public class TransactionContext implements org.gradle.api.ProjectEvaluationListener { //implements org.gradle.api.ProjectEvaluationListener

    private String dbUri;
    private String dbUser;
    private String dbPassword;
    //KONSTRUKTOR
    public TransactionContext(String dbUri, String dbUser, String dbPassword) {
        this.dbUri = dbUri;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        //Gradle.addProjectEvaluationListener(this);
    }
    //KONSTRUKTOR ENDE

    public Connection getDbConnection() {
        if (dbConnection == null) {
            dbConnection = DbConnector.connect(dbUri, dbUser, dbPassword);
        }
        return dbConnection;
    }

    private Connection dbConnection = null;

    public void dbConnectionClose() {
        try {
            if (dbConnection != null) {
                dbConnection.close();
                dbConnection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void dbCommit() {
        try {
            if (dbConnection != null) {
                dbConnection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void beforeEvaluate(Project project) {

    }

    @Override
    public void afterEvaluate(Project project, ProjectState projectState) {
        dbCommit();
    }

}
