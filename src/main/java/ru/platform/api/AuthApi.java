package ru.platform.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.platform.dto.UserDTO;
import ru.platform.request.AuthRequest;
import ru.platform.service.IUserService;
import ru.platform.utils.JwtUtil;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserService service;


    @PostMapping("/signUp")
    public ResponseEntity<?> userSave(@RequestBody UserDTO user){
        try {
            return ResponseEntity.ok(service.createUser(user));
        }
        catch (AuthenticationException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            String token = jwtUtil.generateToken(authRequest.getUsername(), roles);

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

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        // Извлечение токена из куки
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Проверка валидности токена
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            List<String> roles = jwtUtil.extractRoles(token);

            // Возвращаем данные пользователя
            return ResponseEntity.ok(Map.of(
                    "username", username,
                    "roles", roles
            ));
        }

        // Если токен отсутствует или невалиден
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
