# Biotech Dashboard

Biotech Dashboard is a Java project for analysis of oncology trial status from ClinicalTrials.gov. The planned dashboard compares completion and early discontinuation across trial phases and sponsor types.

The current code provides the service foundation. Trial imports, analytical endpoints, and the React dashboard are not implemented yet.

## Current scope

- A Spring Boot service with PostgreSQL configuration.
- Flyway migrations, with a baseline migration for an empty database.
- A public health endpoint and a route policy for a future read-only API.
- Integration tests for database migrations and route access.
- CI checks for the build, tests, secrets, and dependencies.

The [implementation plan](docs/plan.md) defines the remaining increments. [Analytical definitions](docs/definitions.md) records the intended cohort and metrics.

## Run locally

Requirements: JDK 21 or later, Maven, and Docker with Compose.

From the repository root, start PostgreSQL:

```sh
docker compose up -d
```

Start the service in the same terminal:

```sh
cd backend
mvn spring-boot:run
```

From another terminal, check the health endpoint:

```sh
curl http://localhost:8080/actuator/health
```

The expected response is `{"status":"UP"}` after the database is ready. No dashboard or trial data endpoint is available in this version.

## Configuration

The Compose file exposes PostgreSQL on `127.0.0.1:5432`. Its default database, username, and password are `biotech`, for local development.

The service accepts `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`. `CORS_ALLOWED_ORIGINS` controls the allowed browser origin.

The [security note](docs/security.md) describes the route policy and database exposure.

## Tests

With Docker active, run from the repository root:

```sh
cd backend
mvn test
```

Testcontainers starts an isolated PostgreSQL container. The tests apply the Flyway migration and check the service's access rules.

## Source map

| Path | Purpose |
| --- | --- |
| `backend/` | Spring Boot service and Maven build |
| `backend/src/main/resources/db/migration/` | Flyway migrations |
| `backend/src/test/` | Integration tests |
| `docker-compose.yml` | Local PostgreSQL instance |
| `docs/` | Plan, analytical definitions, and security decisions |

## Analytical limits

Trial status does not establish clinical efficacy. A completed trial does not establish that its treatment worked. A terminated or withdrawn trial does not establish treatment failure.

The project has no analytical results yet. The planned comparisons will depend on registry coverage, cohort definitions, and the dates of source snapshots.
