package ru.platform.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.platform.request.AuthRequest;
import ru.platform.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String token = jwtUtil.generateToken(authRequest.getUsername());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); // Недоступен через JS
            cookie.setSecure(true); // Доступен только по HTTPS
            cookie.setPath("/"); // Для всех запросов
            cookie.setMaxAge(60 * 60 * 24); // Время жизни cookie (1 день)
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("token", null);
            cookie.setHttpOnly(true); // Сделаем cookie недоступной для JS
            cookie.setSecure(true);   // Сделаем cookie доступной только по HTTPS
            cookie.setPath("/");      // Для всех запросов
            cookie.setMaxAge(0);      // Убираем cookie
            response.addCookie(cookie);

            return ResponseEntity.ok("Logged out successfully");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR!");
        }
    }
}
