# Security note

Public, read-only analytics over public data. No accounts, no credentials, no PHI, no user-submitted
records, no public write operations.

## Assets

TODO.

## Trust boundaries

TODO.

## Controls in application code

- Spring Security allowlist, deny by default
- read-only `GET` and `HEAD` routes only
- bounded query parameters on every analytics endpoint
- upstream base URL owned by configuration, never by a request parameter
- upstream timeouts, bounded retries, backoff
- Actuator health only, no details
- gitleaks and dependency vulnerability check in CI

## CSRF

TODO: record the rationale when the security chain lands in PR 1.

## Left to the hosting platform

TODO: rate limiting, request size limits, TLS.
