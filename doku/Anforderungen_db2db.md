Anforderungen Steps
=================== 

----------
**Kontext**
----------

Verschiedene Datenherren und Datenproduzenten stellen ihre Daten auf unterschiedlichen Plattformen (Oracle, Postgres, SQLite etc.) zur Verfügung. Da diese teilweise periodisch in unsere Postgresql-DB abgelegt werden müssen und für verschiedene Anwendungen benötigt und/oder umgebaut werden müssen, soll es Steps geben, welches einen vordefinierten Import der Daten in unsere DB ermöglicht. 
Eingebettet wird der Step in Gradle, einem Build-Tool, welches die Zusammensetzung mehrerer Steps ermöglicht. 

----------
**db2db-Step**
---------- 

Der db2db-Step soll Tabellen aus einer Datenbank (u.a. PostgreSQL, OracleDB, MSSQL etc.) in eine andere Datenbank kopieren. Eine SQL-Datei definiert dabei den Input (Verschiedene Tabellen, Join, Union etc.). Was in diesem SQL-file steht, ist in der Verantwortung des Benutzers!  Dieser Input wird dann in die Zieltabelle geschrieben, welche zuvor optional geleert wird (Truncate). Das Updaten einer Tabelle wird vorerst noch nicht implementiert. Alle Schritte innerhalb dieses Steps sollen in einer Transaktion pro DB ausgeführt werden.
 

**benötigte Parameter** :

- Input DB-Connection
- Output DB-Connection
- Truncate? 
- SQL-file mit Input
- Output Schema.Table (qualifizierte Namen)

**Weitere überlegungen** :

- Input-Connection darf nur lesend sein. 

**Interface** :

public interface Db2DbStep {

    public void execute(Connection sourceDb, Connection targetDb, List< TransferSet > transferSets);
}

public class TransferSet {


    private Boolean truncate;
    private File inputSqlFile;
    private String outputQualifiedSchemaAndTableName;

    public void TransferSet(Boolean truncate, File inputSqlFile, String outputQualifiedSchemaAndTableName){
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

**Logging** :

Es soll zwei Log-Stufen geben (normal und advanced). In der Stufe "normal" werden nur die für den Benutzer zentralen Ein- und Ausgaben geloggt. In der Stufe "advanced" werden alle für eine Fehlersuche auf Stufe Benutzer (Also nicht Entwickler) relevanten Informationen (z:b. auch SQL Statements) geloggt. 

log(String Message, Integer LogLevel)

Beim db2db-Step soll für jedes Transferset einen Logeintrag angelegt werden (Wie viele Zeilen wurden gelesen, wie viele wurden geschrieben, wurde die Tabelle zuvor geleert?). Im Advanced-Level wird zusätzlich auch noch die SQL-Query geloggt. 

**Dokumentation** :

Die Dokumentation des db2db-Steps soll zwei Teile enthalten: Die Anleitung für den Benutzer (Guide) sowie die Dokumentation der Entwicklung (API-Dokumentation). 

**Abgrenzung** : 

Der db2db-Step enthält kein GUI. Ebenfalls ist das Hauptprogramm (In diesem Fall Gradle) nicht Teil dieses Steps. 
