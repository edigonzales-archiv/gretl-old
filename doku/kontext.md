# Kontext


Verschiedene Datenherren und Datenproduzenten stellen ihre Daten auf unterschiedlichen Plattformen (Oracle, Postgres, SQLite etc.) zur Verfügung. Da diese teilweise periodisch in unsere Postgresql-DB abgelegt werden müssen und für verschiedene Anwendungen benötigt und/oder umgebaut werden müssen, soll es Steps geben, welches einen vordefinierten Import der Daten in unsere DB ermöglicht. Es soll zudem auch möglich sein Tabellen innerhalb einer Datenbank mittels SQL-Queries umzubauen. Die Verantwortung bezüglich dem Inhalt der verwendeten SQL-Queries liegt beim Benutzer der Steps.
Eingebettet werden die Steps in Gradle, einem Build-Tool, welches die Zusammensetzung mehrerer Steps ermöglicht. Dieses Build-Tool Gradle bildet sogleich auch das Hauptprogramm, von wo die Steps bedient werden.

## GUI 
Es wird kein zusätzliches GUI (Graphical User Interface) erstellt.

## Logging 
Über sämtliche Steps hinweg gibt es ein Logging. 

## Setup
Es wird kein Setup vorgenommen.

## Dokumentation
Für den Benutzer wird eine Benutzerdokumentation und ein Referenzhandbuch erstellt. 
