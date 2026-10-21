# Changelog

## Unreleased — 2026-10-21 (maintenance draft)

### Security
- Removed fixed JWT secret and moved it to a mandatory `APP_JWT_SECRET` environment variable.
- Removed embedded MySQL and Baidu OCR credentials; moved runtime configuration to environment variables.
- Removed public administrator/game-user bootstrap routes and a legacy unauthenticated test mutation endpoint.
- Disabled bypassable SMS test-code login and automatic scheduled prototype tasks.
- Changed new password storage to BCrypt; legacy plaintext passwords are upgraded after successful login (existing accounts still require password resets).
- Protected `/v1/**` and `/file/**` with JWT verification; added per-user UUID check to game endpoints.
- Constrained CORS configuration and file-download path handling.
- Removed sample user data rows from the historical SQL file.

### Maintenance
- Aligned Maven Java compiler settings to Java 17; removed unused Oracle and legacy commons-fileupload dependencies.
- Removed old hardcoded frontend API host, debug header, and several sensitive debug traces.
- Cleaned generated `frontend/node_modules` and `frontend/unpackage` from the distributable snapshot.
- Added accurate setup documentation, source hygiene tests, basic security checklist, and GitHub Actions.

### Unresolved
- Original Git history can still expose credentials; rotation and history review are mandatory before making the existing repository public.
- Old frontend/backend routes are not fully integrated; Java build and full app runtime were not validated.
- Third-party assets, licensing and dependency security need a maintainer review.
