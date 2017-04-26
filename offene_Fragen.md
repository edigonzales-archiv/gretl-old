# offene Fragen

 - Zwischen den beiden Steps gibt es keine klare Trennung. Unser Vorschlag:
	 - db2db-Step macht lediglich eine Kopie der gesamten Tabelle (Keine Manipulationen)
	 - sqlexecuter-Step macht alle Manipulationen aber keine Transaktionen zwischen DBs
 - Updaters von Teilen der Tabellen in einem späteren Schritt realisieren?
 - db2db: Ist es sinnvoll  Select- und Insert-Statements ins separaten Transaktionen durchzuführen anstatt gemeinsam in einem Statement?
 - db2db: Was ist eine RAM-Tabelle?
 - sqlExecutor: Was bedeutet "1:1-Step innerhalb gleicher DB"?
 - sqlExecuter: Finden die Datenumbauten lediglich in PostgreSQL-DBs statt?
 - sqlExecuter: Wie sieht eine komplexe Query aus?
 - Damit die Steps möglich sind müssen aber Schemas/Tabellen angelegt und gelöscht werden dürfen.


