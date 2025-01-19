package ru.platform.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.platform.dto.UserDTO;
import ru.platform.request.AuthRequest;
import ru.platform.response.AuthResponse;
import ru.platform.service.IAuthService;
import ru.platform.service.IUserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> userSave(@RequestBody UserDTO user){
        try {
            return ResponseEntity.ok(userService.createUser(user));
        }
        catch (AuthenticationException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        AuthResponse authResponse = new AuthResponse();
        try {
            authResponse = authService.trySignup(authRequest, response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.ok(Map.of(
                "token", authResponse.getToken(),
                "roles", authResponse.getRoles()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            authService.setTokenCookie(null, response);
            return ResponseEntity.ok("Logged out successfully");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR!");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        return authService.checkAuthentication(request);
    }
}
