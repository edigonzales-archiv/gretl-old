Anforderungen Steps
===================


----------
**db2db-Step**
---------- 

Der db2db-Step soll Tabellen aus unterschiedlichen Datenbanken (u.a. PostgreSQL, OracleDB, MSSQL etc.) n:1 in eine andere Datenbank kopieren. Eine SQL-Datei definiert dabei den Input (Verschiedene Tabellen, Join, Union etc.). Dieser Input wird dann in die Zieltabelle geschrieben, welche zuvor optional geleert werden (Truncate). Das Updaten einer Tabelle wird vorerst noch nicht implementiert. Alle Schritte innerhalb dieses Steps sollen Transaktionssicher sein (*con.setAutoCommit(false);*).
 

**benötigte Parameter** :

- Input-URL (DB-Connection)
- Output-URL (DB-Connection)
- Truncate? 
- SQL-file mit Input
- Output Schema.Table

**Weitere überlegungen** :

- PostgreSQL temporary tables als Statingtabellen
- Input-Connection darf nur lesend sein. 
