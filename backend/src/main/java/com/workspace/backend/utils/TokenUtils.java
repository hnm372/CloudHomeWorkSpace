package com.workspace.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.workspace.backend.entity.User;
import com.workspace.backend.entity.UserLog;
import com.workspace.backend.service.UserLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/** Signs and verifies expiring session tokens with an externally configured secret. */
@Component
public class TokenUtils {
    private static final long EXPIRE_TIME_MILLIS = 24L * 60 * 60 * 1000;
    private static final String ISSUER = "cloudhomeworkspace";

    private final UserLogService userLogService;
    private final Algorithm algorithm;

    public TokenUtils(UserLogService userLogService, @Value("${app.jwt-secret}") String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("APP_JWT_SECRET must contain at least 32 characters");
        }
        this.userLogService = userLogService;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String sign(User user, Integer method) {
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME_MILLIS);
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUuid())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
        userLogService.save(new UserLog()
                .setUserUuid(user.getUuid())
                .setMethod(method)
                .setLoginDate(LocalDateTime.now()));
        return token;
    }

    /** Returns the authenticated user UUID, or null if the token is invalid. */
    public String verifyAndGetUuid(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
            String uuid = jwt.getSubject();
            return uuid == null || uuid.isBlank() ? null : uuid;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean verify(String token) {
        return verifyAndGetUuid(token) != null;
    }
}
