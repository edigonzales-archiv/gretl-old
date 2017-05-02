# Kontext

Verschiedene Datenherren stellen ihre Daten auf unterschiedlichen Plattformen (Oracle, Postgres etc.) zur Verfügung. Da diese teilweise in unsere Postges-DB abgelegt werden müssen oder für verschiedene Anwendungen benötigt werden, soll es ein einfaches Tool geben, welches einen schnellen (temporären) Import der Daten in unsere DB ermöglicht. Gegebenenfalls sollen die Daten aber nicht 1:1 in unsere DB importiert werden, sondern entsprechend angepasst und evtl. mit weiteren Tabellen kombiniert werden. Auch bereits in der Datenbank bestehende Daten sollen mittels eines einfachen Tools entsprechend angepasst werden können.
Diese Prozesse können periodisch oder nach Wunsch des Benutzers ausgeführt werden und sollen voneinander getrennt ansprechbar sein.


# Anforderungen SQLExecutor-Step

Der SQLExecuter-Step wird dazu benötigt um Datenumbauten von Tabellen in der gleichen DB vorzunehmen. Die Daten für den Datenumbau können aus verschiedenen Tabellen verschiedener Schemen zusammengezogen werden. Dabei besteht einzig die Anforderungen, dass die Daten aus der gleichen Datenbank stammen. Mit der in einem oder mehreren sql-Files abgelegten SQL-Queries werden die Daten umgebaut und gemäss der Query in einer oder mehreren Tabellen abgespeichert. 
Damit bei einem allfälligen Fehler keine Daten verloren gehen, müssen die Datenumbauten transaktionssicher sein. Das heisst, dass bei einem Fehler die Daten den Stand von vor der Transaktion haben. Hierfür wird mit Staging-Tabellen gearbeitet. Dazu werden die Daten umgebaut und in eine leere Staging-Tabelle geschrieben. Funktioniert dies fehlerfrei, so werden die Daten anschliessend in die Zieltabelle geschrieben. Wobei diese zuerst geleert und anschliessend abgefüllt wird.
Es soll lediglich ein Staging-Schema pro DB geben.

Input:

* connection
* Query-Files als Array

Output:

* umgebaute Tabelle(n)
* Umbau erfolgreich? ja/nein
