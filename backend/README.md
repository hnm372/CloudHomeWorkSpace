# Backend (legacy Spring Boot service)

- Java 17 / Spring Boot 3.0.2 / MyBatis-Plus / MySQL.
- For reproducible local setup, environment variables and operational cautions, see [the repository README](../README.md).
- `application-dev.yml` and `application-prod.yml` no longer store working database or OCR passwords.
- The original sample user records have been removed from the checked-in SQL schema.
- `/public/gadmin`, `/v1/gguser`, `/test/modify` and the hardcoded SMS test login have been disabled or removed.
- Authenticated game routes require a token and match the JWT user UUID to the requested UUID.
- The backend does **not** currently provide the `/file/upload` route expected by the frontend.
- All deployments require new credentials, a history scan and a dependency audit.
