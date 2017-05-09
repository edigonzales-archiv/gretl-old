# Kontext


Verschiedene Datenherren und Datenproduzenten stellen ihre Daten auf unterschiedlichen Plattformen (Oracle, Postgres, SQLite etc.) zur Verfügung. Da diese teilweise periodisch in unsere Postgresql-DB abgelegt werden müssen und für verschiedene Anwendungen benötigt und/oder umgebaut werden müssen, soll es Steps geben, welches einen vordefinierten Import der Daten in unsere DB ermöglicht. 
Eingebettet wird der Step in Gradle, einem Build-Tool, welches die Zusammensetzung mehrerer Steps ermöglicht. 


# Anforderungen SQLExecutor-Step

Der SQLExecuter-Step wird dazu benötigt um Datenumbauten von Tabellen in der gleichen DB vorzunehmen. Die Daten für den Datenumbau können aus verschiedenen Tabellen verschiedener Schemen zusammengezogen werden. Dabei besteht einzig die Anforderungen, dass die Daten aus der gleichen Datenbank stammen. Mit der in einem oder mehreren sql-Files abgelegten SQL-Queries werden die Daten umgebaut und gemäss der Query in einer oder mehreren Tabellen abgespeichert. 
Damit bei einem allfälligen Fehler keine Daten verloren gehen, müssen die Datenumbauten transaktionssicher sein. Das heisst, dass bei einem Fehler die Daten den Stand von vor der Transaktion haben. Hierfür werden alle SQL-Statements in einer einzigen Transaktion ausgeführt. Funktioniert dies fehlerfrei, so werden die Daten anschliessend in die Zieltabelle geschrieben. Soll die Tabelle im vorhinein geleert werden, so muss dies im sql-Skript definiert sein.
Die SQL-Files werden entsprechend der Reihenfolge im Array abgehandelt.

Input:

* connection
* SQL-Files als Array

Output:

* kein Output
