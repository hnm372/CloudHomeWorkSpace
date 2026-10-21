# CloudHomeWorkSpace

> **Status: legacy prototype, under maintenance. Not production-ready.**
> **安全提示：这是恢复维护中的旧项目，不能直接部署到公网。**

CloudHomeWorkSpace is an independently maintained, experimental full-stack project exploring image-and-text proofreading, Word document handling and some prototype user/game APIs. The UI and backend were developed separately and **are not yet fully integrated**.

## Components / 项目结构

| Path | Technology | Purpose |
| --- | --- | --- |
| `backend/` | Java 17, Spring Boot 3.0.2, MyBatis-Plus, MySQL | Authentication, game prototype routes, document utilities, OCR integration |
| `frontend/` | Vue 2 / uni-app / uView UI | Experimental image upload / proofreading UI and WeChat mini-program scaffolding |
| `backend/src/main/resources/db/workspace.sql` | MySQL SQL | Historical schema **without the original sample user data** |
| `tests/` | Python standard library | Non-functional source hygiene checks |

**Important limitations:** The frontend calls `/file/upload`, but the supplied backend contains **no upload endpoint**. The old frontend also references user profile flows that have no matching backend route. Image/text comparison is therefore **not currently a working end-to-end feature**. Game routes are legacy/prototype functionality. Do not advertise these as complete.

## Getting started / 本地启动后端

1. Install **JDK 17+**, **MySQL 8** and **Maven 3.8+** (or use `backend/mvnw`).
2. Review `backend/src/main/resources/db/workspace.sql` and create/import an isolated local database named `workspace`. Treat the schema as **historical and unverified**: some old MySQL column declarations may need migration.
3. Export the settings listed below (use your own development credentials, never actual production credentials):

   ```sh
   export SPRING_PROFILES_ACTIVE=dev
   export DB_URL='jdbc:mysql://127.0.0.1:3306/workspace?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC'
   export DB_USERNAME='your_local_db_user'
   export DB_PASSWORD='your_local_db_password'
   export APP_JWT_SECRET='replace-with-a-unique-random-secret-of-at-least-32-characters'
   export APP_UPLOAD_PATH='./var/uploads'
   export APP_DOWNLOAD_PATH='./var/uploads'
   ```

4. From `backend/`, run `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`).

`APP_JWT_SECRET` is mandatory and deliberately rejects keys shorter than 32 characters. The OCR integration is optional; set `BAIDU_OCR_APP_ID`, `BAIDU_OCR_API_KEY` and `BAIDU_OCR_SECRET_KEY` only when needed. Configure `APP_CORS_ALLOWED_ORIGINS` with a comma-separated list of trusted origins. Config templates live in `application-dev.yml` and `application-prod.yml`.

**Build status:** The Java backend was not built in the preparation environment (Maven and the required third-party dependencies were unavailable). You must validate the build and database compatibility locally before relying on it. `Spring Boot 3.0.2`, `Fastjson 1.2.x` and other dated dependencies warrant an independent dependency/security review before deployment.

## Frontend / 前端运行

Open `frontend/` as a **Vue 2 uni-app project using HBuilderX**. It contains vendored `uni_modules`, but **no standalone `package.json` build scripts**. Do not assume `npm run build` works. Supply `VUE_APP_API_BASE_URL` in the frontend build environment (e.g. `http://localhost:8088` for local work, **HTTPS** for a real deployed API). The old hard-coded public IP has been removed. WeChat builds additionally require checking the WeChat Mini Program AppID and request domain configuration.

## Tests / 测试

- `python3 -m unittest discover -s tests -p 'test_*.py' -v` runs repository hygiene checks (also run by GitHub Actions).
- Java unit tests are under `backend/src/test/java/` and require Maven dependencies to run: `cd backend && ./mvnw test`.
- These checks **do not replace end-to-end, penetration or dependency testing**.

## License / 开源协议

Released under the [MIT License](LICENSE). The repository history was reset before publication; the original credentials from the first upload must still be treated as compromised and rotated. See [`docs/PUBLIC_RELEASE_CHECKLIST.md`](docs/PUBLIC_RELEASE_CHECKLIST.md).

## Contributing / 参与贡献

Issue reports and focused pull requests are welcome under the MIT License. Please describe the bug, reproduction steps, Java/uni-app version and proposed fix. Do not include access tokens, passwords, personal data or proprietary code in issues.

See [`CHANGELOG.md`](CHANGELOG.md) for the first maintenance pass and [`docs/KNOWN_LIMITATIONS.md`](docs/KNOWN_LIMITATIONS.md) for what remains to be done.
