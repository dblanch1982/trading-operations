# trading-operations

## Mit Docker Compose starten

```sh
docker compose up --build
```

Die Anwendung ist anschließend unter `http://localhost:8080` erreichbar. Der H2-Datenbestand bleibt im benannten Volume `operations-data` erhalten. `docker compose down -v` entfernt auch dieses Volume und damit die gespeicherten Daten.