# Agent instructions

## Project overview

- Java 25 / Spring Boot Maven WAR application; versions and dependencies are defined in `pom.xml`.
- Main code is under `src/main/java/com/trading/mcp/operations`.
- Thymeleaf page: `OperationPageController` serves `/` using `templates/index.html`.
- REST API: `OperationController` provides `GET` and `POST /api/operations` and `DELETE /api/operations/{id}`.
- MCP server uses Streamable HTTP and exposes read-only `list_operations` and `get_operation` tools through `OperationMcpTools`.
- `OperationService` persists and reads operations through `OperationRepository`; it does not place or simulate broker orders.
- `TradingOperation` is the JPA entity. Keep its persistence mapping and repository tests consistent when changing fields.

## Implementation guidance

- Keep page rendering in `@Controller` classes and JSON endpoints in `@RestController` classes.
- Keep HTTP concerns in controllers and persistence/conversion in the service and repository layers. Keep API payloads in `OperationDto`.
- Saving an operation must not be described as executing a trade. There is no broker integration.
- Keep MCP tools read-only unless authentication and authorization have been designed and reviewed; tool hints are not access control.
- For behavior changes, add or update focused tests in the existing JUnit test packages. Use `OperationRepositoryTest` for persistence behavior and `OperationsApplicationTests` for full-context/page integration.
- Follow the existing package structure and constructor injection. Avoid unrelated formatting or dependency changes.

## Validation

Run the relevant checks from the repository root:

```powershell
.\mvnw.cmd test
docker compose config --quiet
docker compose up --build --detach
curl.exe --fail http://localhost:8080/
curl.exe --fail http://localhost:8080/api/operations
```

The GitHub Actions workflow at `.github/workflows/ci.yml` runs the Compose validation, image build, and HTTP smoke checks. Report commands that could not be run as unverified; never claim a test or build passed without its result.

## Container and data safety

- Compose exposes host port `8080` and maps it to the application container port `3000`; `SERVER_PORT` and `Dockerfile` must remain aligned with that port.
- Compose binds the MCP/HTTP port to loopback by default. Do not remove that restriction without adding a suitable security boundary.
- H2 data is stored in the named `operations-data` volume. Do not remove that volume or run `docker compose down --volumes` outside an isolated test environment unless explicitly requested.
- Review actuator exposure and authentication before deployment. `src/main/resources/application.yaml` currently exposes all actuator web endpoints.
