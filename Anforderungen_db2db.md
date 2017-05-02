Anforderungen Steps
=================== 

----------
**Kontext**
----------

Verschiedene Datenherren stellen ihre Daten auf unterschiedlichen Plattformen (Oracle, Postgres etc.) zur Verfügung. Da diese teilweise in unsere Postges-DB abgelegt werden müssen oder für verschiedene Anwendungen benötigt werden, soll es ein einfaches Tool geben, welches einen schnellen (temporären) Import der Daten in unsere DB ermöglicht. Gegebenenfalls sollen die Daten aber nicht 1:1 in unsere DB importiert werden, sondern entsprechend angepasst und evtl. mit weiteren Tabellen kombiniert werden. Auch bereits in der Datenbank bestehende Daten sollen mittels eines einfachen Tools entsprechend angepasst werden können.
Diese Prozesse können periodisch oder nach Wunsch des Benutzers ausgeführt werden und sollen voneinander getrennt ansprechbar sein.

----------
**db2db-Step**
---------- 

Der db2db-Step soll Tabellen aus unterschiedlichen Datenbanken (u.a. PostgreSQL, OracleDB, MSSQL etc.) 1:1 in eine andere Datenbank kopieren. Eine SQL-Datei definiert dabei den Input (Verschiedene Tabellen, Join, Union etc.). Dieser Input wird dann in die Zieltabelle geschrieben, welche zuvor optional geleert werden (Truncate). Das Updaten einer Tabelle wird vorerst noch nicht implementiert. Alle Schritte innerhalb dieses Steps sollen in einer Transaktion pro DB ausgeführt werden.
 

**benötigte Parameter** :

- Input-URL (DB-Connection)
- Output-URL (DB-Connection)
- Truncate? 
- SQL-file mit Input
- Output Schema.Table (qualifizierte Namen)

**Weitere überlegungen** :

- Input-Connection darf nur lesend sein. 
