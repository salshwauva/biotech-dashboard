-- Baseline migration. Flyway owns every schema change in this project.
-- Hibernate runs with ddl-auto=validate and never creates or alters a table.
-- The first domain tables (ingestion_run, raw_snapshot) arrive in PR 2.
-- This migration exists so the migration pipeline is proven from an empty database.

CREATE SCHEMA IF NOT EXISTS public;
