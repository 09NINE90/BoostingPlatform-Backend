package ru.platform.user.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthResponse;
import ru.platform.user.service.IAuthService;
import ru.platform.utils.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse trySignup(LoginUserRqDto userRqDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRqDto.getUsername(), userRqDto.getPassword())
            );
            String role = authentication.getAuthorities().toString().replace("[", "").replace("]", "");
            String token = jwtUtil.generateToken(userRqDto.getUsername(), role);
            log.info("User {} signed up", userRqDto.getUsername());
            return new AuthResponse(token, role);
        }catch (Exception e) {
            log.error("Failed authorization attempt. User {}", userRqDto.getUsername(), e);
            throw new RuntimeException("Failed authorization attempt");
        }
    }

    public static String checkToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
