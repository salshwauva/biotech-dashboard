# Open Data Biotech Dashboard

Spring Boot service that ingests ClinicalTrials.gov API v2 oncology studies, keeps immutable source
snapshots, normalizes validated records into PostgreSQL, and serves trial-disposition analytics to a
React dashboard.

Analytical question: how do oncology trial completion and early-discontinuation patterns differ by
trial phase and sponsor type?

Trial disposition is not clinical efficacy. `Completed` does not mean the intervention worked.
`Terminated` and `Withdrawn` do not mean it failed.

## Status

PR 1 (foundation) in progress. See `docs/plan.md` for the increment sequence.

## Layout

| Path | Contents |
| --- | --- |
| `backend/` | Spring Boot service, Maven build, Flyway migrations |
| `frontend/` | React + Vite dashboard (added in PR 6) |
| `docs/` | Analytical definitions, security note, plan, metrics |
| `.github/workflows/` | CI: build, tests, gitleaks, dependency check |

## Run locally

Requirements: JDK 21 or later, Maven, Docker.

1. Start PostgreSQL: `docker compose up -d`
2. Run the service: `cd backend && mvn spring-boot:run`
3. Check health: `curl localhost:8080/actuator/health`

## Run the tests

`cd backend && mvn test`

The tests start their own PostgreSQL container and apply the Flyway migrations to it. Docker Engine
29 refuses the API version that Testcontainers asks for by default, so `backend/pom.xml` pins
`api.version` to 1.44 for the test run.

## Architecture

TODO: diagram after PR 3.

## Methodology and definitions

See `docs/definitions.md`.

## Limitations

TODO.

## Security posture

See `docs/security.md`.

## What I would do next

TODO.
