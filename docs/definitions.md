# Analytical definitions

Every definition here is a contract. Code, tests, and the dashboard follow it. No aggregate query
ships before the definition it depends on is filled in below.

Status: all five are open. PR 4 cannot start until they are closed.

## 1. Oncology cohort

Open.

Record:
- source field(s) used
- match type: exact, categorical, or text
- handling of ambiguous records
- known limitations

## 2. Trial disposition categories

Open.

Source statuses stay distinct in storage. Dashboard grouping is derived, not stored in place of the
source value. Category names never use `success` or `failure`.

## 3. Denominators

Open.

Every percentage on the dashboard names its denominator. Decide explicitly whether ongoing and
recruiting trials are excluded.

## 4. Sponsor type mapping

Open.

Source classification stays in storage. The dashboard grouping is derived separately.

## 5. Time axis

Open. Include a time view only after this is closed.

Candidates: study start year, or completion year. One axis only.

## Unknown enum policy

Open. Unknown upstream values are never mapped to a known category.
