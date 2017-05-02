Im Verzeichnis svn_ignorelist wird die projektspezifische ignorelist geführt welche nach einer Anpassung auf alle SVN-Clients angewendet werden muss.

Befehl zum anwenden / aktualisieren der ignorelist:

svn propset svn:ignore -F ./svn_ignorelist/svn_ignorelist.txt

!Achtung: Die Pfade in ignorelist.txt beziehen sich relativ auf das Rootverzeichnis des Projektes ([UserHome]/IdeaProjects/gretl). Der svn Befehl muss entsprechend auch aus dem Roorverzeichnis abgesetzt werden.

