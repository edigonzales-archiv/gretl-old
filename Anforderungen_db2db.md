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

Der db2db-Step soll Tabellen aus einer Datenbank (u.a. PostgreSQL, OracleDB, MSSQL etc.) in eine andere Datenbank kopieren. Eine SQL-Datei definiert dabei den Input (Verschiedene Tabellen, Join, Union etc.). Dieser Input wird dann in die Zieltabelle geschrieben, welche zuvor optional geleert wird (Truncate). Das Updaten einer Tabelle wird vorerst noch nicht implementiert. Alle Schritte innerhalb dieses Steps sollen in einer Transaktion pro DB ausgeführt werden.
 

**benötigte Parameter** :

- Input-URL (DB-Connection)
- Output-URL (DB-Connection)
- Truncate? 
- SQL-file mit Input
- Output Schema.Table (qualifizierte Namen)

**Weitere überlegungen** :

- Input-Connection darf nur lesend sein. 
