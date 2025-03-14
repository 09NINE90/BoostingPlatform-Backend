package ru.platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.platform.LocalConstants.Variables.TEN_HOURS;
import static ru.platform.LocalConstants.Variables.TEN_MINUTES;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret-key}")
    private static String SECRET_KEY = "ajscqSVPNj4GNzF+Ln2H6yaE2etWGExa618+TDP96ZE=";

    private static final long EXPIRATION_TIME = TEN_HOURS;
    private static final long EXPIRATION_TIME_CONFIRMATION_LINK = TEN_MINUTES;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);
        return role != null ? role : ""; // Возвращаем пустой список, если роли отсутствуют
    }

    public LocalDateTime extractDateStart(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String dateStart = claims.get("dateStart", String.class);
        return DateUtil.getDateTimeFromString(dateStart);
    }

    public String extractUserPassword(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("password", String.class);
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

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

