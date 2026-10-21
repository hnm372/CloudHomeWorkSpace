package com.workspace.backend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {
    @Test
    void hashesNewPasswordsAndValidatesThem() {
        String encoded = PasswordUtils.hashPassword("Passw0rd!");
        assertNotEquals("Passw0rd!", encoded);
        assertTrue(PasswordUtils.isHashed(encoded));
        assertTrue(PasswordUtils.matches("Passw0rd!", encoded));
        assertFalse(PasswordUtils.matches("wrong", encoded));
    }

    @Test
    void supportsOneTimeLegacyMigration() {
        assertTrue(PasswordUtils.matches("legacyPassword", "legacyPassword"));
        assertFalse(PasswordUtils.isHashed("legacyPassword"));
        assertFalse(PasswordUtils.matches("wrongPassword", "legacyPassword"));
    }

    @Test
    void generatesPasswordsWithRequestedLength() {
        for (int i = 0; i < 10; i++) {
            assertEquals(16, PasswordUtils.getNormalPassword(16).length());
        }
        assertThrows(IllegalArgumentException.class, () -> PasswordUtils.getNormalPassword(2));
    }
}
