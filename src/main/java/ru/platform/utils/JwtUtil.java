package ru.platform.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.platform.exception.PlatformException;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.platform.LocalConstants.Variables.TEN_HOURS;
import static ru.platform.LocalConstants.Variables.TEN_MINUTES;
import static ru.platform.exception.ErrorType.AUTHORIZATION_ERROR;
import static ru.platform.exception.ErrorType.TOKEN_EXPIRED_ERROR;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret-key}")
    private static String SECRET_KEY = "ajscqSVPNj4GNzF+Ln2H6yaE2etWGExa618+TDP96ZE=";

    private static final long EXPIRATION_TIME = TEN_HOURS;
    private static final long EXPIRATION_TIME_CONFIRMATION_LINK = TEN_MINUTES;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String id, String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
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

    public LocalDateTime extractDateStart(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String dateStart = claims.get("dateStart", String.class);
            return DateUtil.getDateTimeFromString(dateStart);
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
                .claim("dateStart", DateUtil.getStringFromDateTime(LocalDateTime.now()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_CONFIRMATION_LINK))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractTokenFromRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

