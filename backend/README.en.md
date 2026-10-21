# CloudHomeWorkSpace backend

This is a legacy **prototype**, not a production-ready API. It uses Java 17, Spring Boot 3.0.2, MyBatis-Plus and MySQL. See the [project README](../README.md) for configuration and run instructions.

This maintenance pass removes checked-in database/OCR credentials, requires a strong externally configured JWT secret, hashes new passwords using BCrypt, disables unsafe bootstrap/test endpoints and restricts protected routes. Existing plaintext passwords are upgraded on a successful login; rotate affected real passwords. Some UI endpoints are still missing from the backend (especially `/file/upload`). Do not expose the service publicly without a security review and successful integration tests.
