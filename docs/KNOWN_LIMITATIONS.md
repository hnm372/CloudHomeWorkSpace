# Known limitations and next maintenance milestones

1. **End-to-end image/document workflow unfinished.** The frontend calls `/file/upload`, but the backend only exposes `/file/download` in `FileController`. OCR work exists in `CompareServiceImpl`, but is not wired into a complete tested upload route.
2. **Authentication UI not integrated.** The frontend uses legacy `username` and other fields; backend registration/login require `userAccount`, `kaptcha` and a session challenge. `getUserInfo` still points to a route not provided by backend. The frontend is a prototype.
3. **Historical SQL schema needs migration testing.** Its MySQL types and old field definitions may require correction. Existing accounts may have plaintext passwords; this patch hashes new passwords and upgrades legacy values on login only.
4. **No end-to-end runtime verification.** Spring Boot/Maven dependencies were unavailable when the patch was prepared. Static checks are green if CI passes, but do not prove the backend compiles.
5. **Dependency review outstanding.** Versions of Spring Boot, Fastjson, OCR utilities and vendored frontend libraries are dated and should be upgraded deliberately with tests, rather than blindly changing numbers.
6. **Deployment/security pending.** User authentication and authorization, rate limits, file types, OCR handling and token revocation require deeper review. No root open-source license has been chosen yet.
7. **Non-production cron/demo modules.** The original scheduled `play()` job and generated admin/test-account endpoints are disabled for safety. Review game prototypes before re-enabling any scheduled tasks.

Suggested real maintenance milestones: (a) run a clean backend build and schema migration; (b) add upload/compare integration tests and a working endpoint; (c) redesign UI registration/login with captcha support; (d) scan/upstream dependencies and third-party licenses; (e) create an intentionally licensed release with a clean history.
