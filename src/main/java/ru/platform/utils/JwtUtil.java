package ru.platform.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.AeadAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.platform.exception.PlatformException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static ru.platform.LocalConstants.DateTimeConstants.*;
import static ru.platform.exception.ErrorType.AUTHORIZATION_ERROR;
import static ru.platform.exception.ErrorType.TOKEN_EXPIRED_ERROR;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret-key}")
    private String secret;

    private SecretKey key;

    private static final long EXPIRATION_TIME_ACCESS = TEN_MINUTES;
    private static final long EXPIRATION_TIME_REFRESH = TWENTY_FOUR_HOURS;
    private static final long EXPIRATION_TIME_CONFIRMATION_LINK = TEN_HOURS;
    private static final AeadAlgorithm ENCRYPTION_ALGORITHM = Jwts.ENC.A256GCM;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Arrays.copyOf(
                secret.getBytes(StandardCharsets.UTF_8),
                32
        );
        key = new SecretKeySpec(keyBytes, "AES");
    }

    public String generateAccessToken(String id, String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", id)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS))
                .encryptWith(key, ENCRYPTION_ALGORITHM)
                .compact();
    }

    public String generateRefreshToken(String id, String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", id)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH))
                .encryptWith(key, ENCRYPTION_ALGORITHM)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            if (token == null) throw new PlatformException(AUTHORIZATION_ERROR);
            return Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new PlatformException(TOKEN_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }
    }

    public String extractRoles(String token) {
        try {
            if (token == null) throw new PlatformException(AUTHORIZATION_ERROR);
            Claims claims = Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();

            String role = claims.get("role", String.class);
            return role != null ? role : "";
        } catch (ExpiredJwtException e) {
            throw new PlatformException(TOKEN_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }

    }

    public String extractUserid(String token) {
        try {
            if (token == null) throw new PlatformException(AUTHORIZATION_ERROR);
            Claims claims = Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();

            String userId = claims.get("userId", String.class);
            return userId != null ? userId : "";
        } catch (ExpiredJwtException e) {
            throw new PlatformException(TOKEN_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }
    }

    public String extractUserPassword(String token) {
        try {
            Claims claims = Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();

            return claims.get("password", String.class);
        } catch (ExpiredJwtException e) {
            throw new PlatformException(TOKEN_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }
    }

    public String generateConfirmationToken(String username, String password) {
        return Jwts.builder()
                .subject(username)
                .claim("password", password)
                .claim("dateStart", DateTimeUtils.getStringFromDateTime(LocalDateTime.now()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_CONFIRMATION_LINK))
                .encryptWith(key, ENCRYPTION_ALGORITHM)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

