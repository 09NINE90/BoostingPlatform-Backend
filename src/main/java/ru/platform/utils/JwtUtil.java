package ru.platform.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.platform.exception.PlatformException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.platform.LocalConstants.DateTimeConstants.TEN_HOURS;
import static ru.platform.LocalConstants.DateTimeConstants.TEN_MINUTES;
import static ru.platform.exception.ErrorType.AUTHORIZATION_ERROR;
import static ru.platform.exception.ErrorType.TOKEN_EXPIRED_ERROR;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret-key}")
    private String secret;

    private Key key;

    private static final long EXPIRATION_TIME_ACCESS = TEN_MINUTES;
    private static final long EXPIRATION_TIME_REFRESH = TEN_HOURS;
    private static final long EXPIRATION_TIME_CONFIRMATION_LINK = TEN_HOURS;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String id, String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String id, String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            if (token == null) throw new PlatformException(AUTHORIZATION_ERROR);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
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
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

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
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

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
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("password", String.class);
        } catch (ExpiredJwtException e) {
            throw new PlatformException(TOKEN_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }

    }

    public String generateConfirmationToken(String username, String password) {
        return Jwts.builder()
                .setSubject(username)
                .claim("password", password)
                .claim("dateStart", DateTimeUtils.getStringFromDateTime(LocalDateTime.now()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_CONFIRMATION_LINK))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

