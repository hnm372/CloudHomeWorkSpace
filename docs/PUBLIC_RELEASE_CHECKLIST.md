# PUBLIC RELEASE BLOCKERS — read before changing GitHub visibility

**Do not switch the original private repository to Public yet.** A clean HEAD commit does not clean old Git history. The uploaded ZIP does not include `.git`, so this review could not inspect the original commits, branches, PRs, issues, releases or tags.

## Mandatory before public release

- [ ] **Rotate or revoke the original Baidu OCR `API_KEY` and `SECRET_KEY`**, which were present in `CompareServiceImpl.java` in the uploaded snapshot. Treat them as compromised even if the repository was private.
- [ ] **Rotate database credentials** formerly present in both `application-dev.yml` and `application-prod.yml`; check for reused credentials. Do not include real credentials in repository files.
- [ ] **Replace the original fixed JWT signing secret**, invalidate all previously issued tokens, and set a new unique `APP_JWT_SECRET` (minimum 32 characters).
- [ ] **Review user records and reset affected passwords**: the old application stored account passwords as plaintext, and the SQL backup contained example user records. The new application migrates a legacy plaintext account to BCrypt only on successful login, which does not remove old plaintext passwords from the database until used.
- [ ] **Audit every reachable Git commit, branch, tag, release and any forks** for the old credentials and sample data, not merely the current working tree. If needed, use `git-filter-repo` or a carefully reviewed fresh-history release plan; coordinate history changes with collaborators. Do not rewrite history blindly or lose work.
- [ ] Inspect all source, images, bundled third-party libraries, package copyrights and mini-program assets for publication rights. **Choose and add an actual root LICENSE** as authorized owner; third-party assets may use different licenses.
- [ ] Run the backend tests, dependency vulnerability scan and end-to-end UI/API tests in a fresh environment. Fix missing `/file/upload` functionality and profile endpoint mismatch before calling it complete.
- [ ] Review the backend authentication and authorization thoroughly (token revocation, logout, password resets, login rate limiting, CSRF/session handling, OCR/file upload constraints, Swagger exposure). This patch is not a full application security audit.
- [ ] Ensure the app uses HTTPS and a valid production API domain if deployed, and restrict `APP_CORS_ALLOWED_ORIGINS`.

## History warning / 历史提交风险

Even after committing these updates, GitHub's normal repository Public action exposes **the full prior history**. A secret removed from the latest file remains recoverable from old commits. Rotating it is essential; Git history rewriting may additionally be needed for personal data or sensitive internal files. Prefer keeping the original repo private until a complete history review and cleanup are finished.

## Codex for Open Source

Real maintainer work can be documented by the reviewed code changes and a **genuine** release, but do not fabricate stars, users, commits, benchmarks or approvals. A Public flag alone does not establish eligibility. Describe this accurately as a maintenance restart and legacy prototype, including unresolved functionality.
