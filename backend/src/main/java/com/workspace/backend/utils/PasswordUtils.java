package com.workspace.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public final class PasswordUtils {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+{}|<>?";
    private static final Pattern PASSWORD_V1 = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$");
    private static final Pattern PASSWORD_V2 = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}$");

    private PasswordUtils() {}

    /** Generates a cryptographically random password using at least three character classes. */
    public static String getNormalPassword(int len) {
        if (len < 12 || len > 128) {
            throw new IllegalArgumentException("Password length must be between 12 and 128");
        }
        String password;
        do {
            StringBuilder value = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                value.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
            password = value.toString();
        } while (characterClassCount(password) < 3);
        return password;
    }

    private static int characterClassCount(String value) {
        int classes = 0;
        if (value.matches(".*[0-9].*")) classes++;
        if (value.matches(".*[a-z].*")) classes++;
        if (value.matches(".*[A-Z].*")) classes++;
        if (value.matches(".*[^a-zA-Z0-9].*")) classes++;
        return classes;
    }

    public static boolean passwordCheckV1(String password) {
        return password != null && PASSWORD_V1.matcher(password).matches();
    }

    public static boolean passwordCheckV2(String password) {
        return password != null && PASSWORD_V2.matcher(password).matches();
    }

    public static String hashPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /** Backwards compatible with legacy plaintext accounts; upgrade them after successful login. */
    public static boolean matches(String password, String storedPassword) {
        if (password == null || storedPassword == null) return false;
        if (isHashed(storedPassword)) return PASSWORD_ENCODER.matches(password, storedPassword);
        return MessageDigest.isEqual(password.getBytes(StandardCharsets.UTF_8),
                storedPassword.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean isHashed(String storedPassword) {
        return storedPassword != null && storedPassword.matches("^\\$2[aby]\\$[0-9]{2}\\$.*");
    }
}
