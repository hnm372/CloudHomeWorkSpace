package com.workspace.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/** Requires MySQL, APP_JWT_SECRET and compatible third-party dependencies. */
@SpringBootTest
@Disabled("Integration environment not configured; run with a disposable database")
class BackendApplicationTests {
    @Test
    void contextLoads() {}
}
