package ru.platform.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ru.platform.request.SignInRequest;
import ru.platform.response.AuthResponse;

public interface IAuthService {
    AuthResponse trySignup(SignInRequest signInRequest, HttpServletResponse response);
    ResponseEntity<?> checkAuthentication(HttpServletRequest request);
    void setTokenCookie(String token, HttpServletResponse response);
}
