# trading-operations

## Lokal bauen

Für den Build wird JDK 25 benötigt. Unter Windows:

```powershell
.\mvnw.cmd package
```

Der Maven-Compiler verwendet Lombok explizit als Annotation-Processor, damit
Getter, Setter und Konstruktoren auch mit JDK 25 generiert werden.

## Mit Docker Compose starten

```sh
docker compose up --build
```

Die Anwendung ist anschließend unter `http://localhost:8080` erreichbar. Der H2-Datenbestand bleibt im benannten Volume `operations-data` erhalten. `docker compose down -v` entfernt auch dieses Volume und damit die gespeicherten Daten.

Der MCP-Endpunkt für Streamable HTTP ist unter `http://localhost:8080/mcp` verfügbar. Der Server stellt die schreibgeschützten Tools `list_operations` und `get_operation` bereit. Compose bindet den Port standardmäßig nur an `127.0.0.1`; vor einem Netzwerk-Deployment muss eine geeignete Authentifizierung und Zugriffskontrolle ergänzt werden.