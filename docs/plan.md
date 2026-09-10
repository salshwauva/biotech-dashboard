# Increment plan

One PR each, in order. Target under about 300 changed lines per PR.

## PR 1 - Foundation

- [x] project skeleton, Maven build, package layout
- [x] Docker Compose for PostgreSQL, bound to loopback
- [x] CI: build, tests, gitleaks, dependency vulnerability check
- [x] Flyway `V1__baseline.sql`
- [x] minimal health endpoint
- [x] Spring Security allowlist with deny by default
- [x] Testcontainers integration test that applies migrations

Exit: clean build and a migrated database from scratch in CI.

## PR 2 - ClinicalTrials.gov ingestion

- [ ] typed client on `RestClient`, API v2 base path from configuration
- [ ] pagination that terminates deterministically
- [ ] connection and read timeouts
- [ ] bounded retries with backoff for transient failures
- [ ] immutable `raw_snapshot` table
- [ ] `ingestion_run` table
- [ ] WireMock fixtures: normal page, pagination, empty, missing field, unexpected enum,
      duplicate NCT ID, transient failure

Exit: a recorded paginated fixture ingests with no live network access.

## PR 3 - Normalization, quality, provenance, idempotency

- [ ] `trial` entity, unique NCT ID at the database level
- [ ] `trial_status_history` for observed status changes
- [ ] `data_quality_run` scorecard
- [ ] oncology cohort rule from `docs/definitions.md`
- [ ] provenance link from every trial to its run and snapshot
- [ ] repeated-ingestion integration test

Exit: the same fixture ingests twice with no duplicate logical state, and every record traces to its
source.

## PR 4 - Analytics

Blocked until all five definitions in `docs/definitions.md` are closed.

- [ ] disposition by phase
- [ ] disposition by sponsor type
- [ ] one time-based aggregate, only if its axis is defined

Exit: every aggregate matches a hand-computed fixture.

## PR 5 - Public read-only REST API

- [ ] analytics endpoints, `GET` only
- [ ] explicit response DTOs, no entities and no raw snapshots
- [ ] validated and bounded query parameters
- [ ] consistent error responses
- [ ] security tests: allowlist, unlisted route, rejected write methods
- [ ] production CORS configuration

Exit: the dashboard's data is reachable through the public API alone.

## PR 6 - React dashboard and ship

- [ ] Vite app in `frontend/`
- [ ] disposition by phase and by sponsor type
- [ ] methodology note visible in the UI
- [ ] README architecture diagram, limitations, security note, how to run
- [ ] demo recording, 30 to 60 seconds
- [ ] measured metrics in `docs/project-metrics.md`

Ship after this PR.
