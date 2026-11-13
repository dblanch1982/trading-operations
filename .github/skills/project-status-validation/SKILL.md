---
name: project-status-validation
description: Analyze and validate the health of the trading-operations Spring Boot project.
---

# Projektstatus analysieren und validieren

Nutze diesen Skill, wenn der Projektzustand, die Funktionsfähigkeit oder die Vollständigkeit von `trading-operations` geprüft werden soll. Prüfe den aktuellen Workspace bei jeder Verwendung erneut; der unten dokumentierte Snapshot ist nur ein Ausgangspunkt.

## Vorgehen

1. Lies `pom.xml`, `src/main/resources/application.yaml`, Docker-Konfiguration, Controller, Service, JPA-Modell, Repository und die zugehörigen Tests.
2. Vergleiche die implementierten Routen und die Persistenz mit den Integrationstests. Verifiziere, dass der Seitencontroller Thymeleaf rendert und der REST-Controller nur API-Antworten liefert.
3. Führe die passenden Prüfungen aus. Trenne statische Befunde, tatsächlich ausgeführte Tests und nicht ausführbare Prüfungen klar voneinander. Behaupte niemals, dass ein Build, Test oder Containerstart erfolgreich war, wenn er nicht ausgeführt wurde.
4. Prüfe Docker-Konfigurationen auf Konsistenz zwischen `EXPOSE`, Compose-Port-Mapping und `server.port`. Prüfe außerdem, ob die Datenbankdatei im benannten Volume liegt und ob die Anwendung mit den konfigurierten Rechten darauf schreiben kann.
5. Berichte Findings nach Schweregrad mit Datei/Zeile, Auswirkung und konkreter Behebung. Kennzeichne offene Validierungen und Umgebungsblockaden ausdrücklich.

## Projektbezogene Soll-Prüfungen

- Java- und Spring-Boot-Version sowie Packaging aus `pom.xml` übernehmen, nicht voraussetzen.
- `OperationPageController` liefert `/` als Thymeleaf-View `index`.
- `OperationController` stellt `GET` und `POST /api/operations` sowie `DELETE /api/operations/{id}` bereit.
- Der MCP-Server nutzt Streamable HTTP und stellt aktuell nur lesende Operation-Tools bereit; MCP-Tool-Hinweise ersetzen keine Zugriffskontrolle.
- `TradingOperation` ist eine JPA-Entität mit Primärschlüssel; Repository-Tests müssen Speichern und erneutes Laden prüfen.
- Das Speichern einer Operation persistiert sie, führt aber keine echte Order aus. Eine Broker-Ausführung darf nicht behauptet werden, solange keine echte Integration vorhanden ist.
- Compose muss Daten persistent speichern und darf die H2-Konsole nicht unbeabsichtigt exponieren.
- `.github/workflows/ci.yml` prüft die Compose-Konfiguration, baut und startet den Container, testet die Seiten- und API-Erreichbarkeit und entfernt danach das CI-Testvolume.

## Validierungsbefehle

Auf Windows im Repository-Root:

```powershell
.\mvnw.cmd test
docker compose config
docker compose up --build -d
curl.exe --fail http://localhost:8080/
curl.exe --fail http://localhost:8080/api/operations
```

Auf macOS/Linux verwende `./mvnw test` anstelle des Windows Maven Wrappers. Ein erfolgreicher `docker compose config` bestätigt nur die Compose-Syntax, nicht den Containerstart oder die Erreichbarkeit. Prüfe nach dem Start zusätzlich `docker compose ps` und die Container-Logs. Verwende für Tests mit POST/DELETE ausschließlich Testdaten und eine isolierte Umgebung; lösche keine benannten Datenvolumes, ohne die Auswirkungen zu prüfen.

## Ausgangsbefund dieses Snapshots

- **Statisch geprüft:** Projekt ist ein Spring-Boot-WAR mit Java 25; die Web-Seite und REST-API sind in getrennten Controllern angelegt. JPA-, Controller- und Full-Context-Tests sind vorhanden.
- **Statisch geprüft:** Dockerfile und Compose verwenden Container-Port `3000`; Compose setzt `SERVER_PORT=3000`, während der Standard-Host-Port `8080` bleibt.

- **Nicht verifiziert:** Maven-Tests, Docker-Compose-Validierung und Containerstart wurden in dieser Umgebung nicht ausgeführt, da keine PowerShell-Shell verfügbar war. Diese Punkte sind offen, nicht bestanden oder bestanden, bis sie in einer geeigneten Umgebung tatsächlich geprüft werden.
