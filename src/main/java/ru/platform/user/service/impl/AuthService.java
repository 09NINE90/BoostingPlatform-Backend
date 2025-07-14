package ru.platform.user.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.detail.CustomUserDetails;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IValidationUserService;
import ru.platform.utils.JwtUtil;

import java.util.Map;
import java.util.UUID;

import static ru.platform.LocalConstants.DateTimeConstants.TEN_HOURS;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final IValidationUserService validationUserService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final static String LOG_PREFIX = "AuthService: {}";

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.AUTHORIZATION_USER)
    public Map<String, String> trySignIn(LoginUserRqDto userRqDto, HttpServletResponse response) {
        try {
            log.debug(LOG_PREFIX, "Валидация данных пользователя");
            validationUserService.validateSignInUser(userRqDto);

            checkConfirmationEmail(userRqDto);

            log.debug(LOG_PREFIX, "Процесс авторизации пользователя");
            return userAuthorization(userRqDto, response);
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Ошибка авторизации пользователя");
            throw new PlatformException(AUTHORIZATION_ERROR);
        }
    }

    /**
     * Проверка подтверждения email пользователем
     */
    private void checkConfirmationEmail(LoginUserRqDto userRqDto) {
        log.debug(LOG_PREFIX, "Проверка подтверждения email для: " + userRqDto.getEmail());
        UserEntity user = userRepository.findByUsername(userRqDto.getEmail())
                .orElseThrow(() -> {
                    log.error(LOG_PREFIX, "Пользователь не найден: " + userRqDto.getEmail());
                    return new PlatformException(NOT_FOUND_ERROR);
                });
        if (!user.isEnabled()) {
            log.error(LOG_PREFIX, "Аккаунт не подтвержден: " + userRqDto.getEmail());
            throw new PlatformException(EMAIL_VERIFIED_ERROR);
        }
        log.debug(LOG_PREFIX, "Аккаунт подтвержден: " + userRqDto.getEmail());
    }

    /**
     * Авторизация пользователя в системе
     */
    private Map<String, String> userAuthorization(LoginUserRqDto userRqDto, HttpServletResponse response) {
        String userEmail = userRqDto.getEmail();

        log.debug(LOG_PREFIX, "Начало авторизации: " + userEmail);
        SecurityContextHolder.clearContext();

        log.debug(LOG_PREFIX, "Аутентификация пользователя: " + userEmail);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEmail, userRqDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        String role = getRole(authentication);

        log.debug(LOG_PREFIX, "Генерация токенов для пользователя: " + userEmail);
        String accessToken = jwtUtil.generateAccessToken(userId.toString(), userEmail, role);
        String refreshToken = jwtUtil.generateRefreshToken(userId.toString(), userEmail, role);

        setRefreshTokenCookie(response, refreshToken);
        log.debug(LOG_PREFIX, "Refresh token установлен в cookie");

        return Map.of(
                "accessToken", accessToken,
                "role", role
        );
    }

    /**
     * Получение роли авторизованного пользователя
     */
    private String getRole(Authentication authentication) {
        return authentication
                .getAuthorities()
                .toString()
                .replace("[", "")
                .replace("]", "");
    }

    /**
     * Добавление refresh token в cookie
     */
    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(TEN_HOURS);
        response.addCookie(cookie);
    }

    /**
     * Получение авторизованного пользователя
     */
    @Override
    public UserEntity getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new PlatformException(AUTHORIZATION_ERROR);
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));
    }

    /**
     * Обработка запроса на обновление access токена
     */
    @Override
    @PlatformMonitoring(name = MonitoringMethodType.REFRESH_TOKEN)
    public Map<String, String> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_PREFIX, "Начало обработки запроса на обновление токена");
        String refreshToken = extractRefreshTokenFromCookie(request);

        if (refreshToken == null) {
            log.error(LOG_PREFIX, "Refresh token отсутствует в cookie");
            deleteRefreshTokenCookie(response);
            throw new PlatformException(AUTHORIZATION_ERROR);
        }

        if (!jwtUtil.isTokenValid(refreshToken)) {
            log.error(LOG_PREFIX, "Невалидный refresh token");
            deleteRefreshTokenCookie(response);
            throw new PlatformException(AUTHORIZATION_ERROR);
        }

        if (jwtUtil.isTokenExpired(refreshToken)) {
            log.error(LOG_PREFIX, "Срок действия refresh token истек");
            deleteRefreshTokenCookie(response);
            throw new PlatformException(AUTHORIZATION_ERROR);
        }

        log.debug(LOG_PREFIX, "Извлечение данных пользователя из refresh token");
        String email = jwtUtil.extractUsername(refreshToken);
        String role = jwtUtil.extractRoles(refreshToken);
        String userId = jwtUtil.extractUserid(refreshToken);

        log.debug(LOG_PREFIX, "Генерация нового access token");
        String newAccessToken = jwtUtil.generateAccessToken(userId, email, role);

        return Map.of(
                "accessToken", newAccessToken,
                "role", role
        );
    }

    /**
     * Получение refresh token из cookie
     */
    public static String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Запрос на выход пользователя из системы
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        deleteRefreshTokenCookie(response);
    }

    /**
     * Удаление refresh token из cookie
     */
    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        log.debug(LOG_PREFIX, "Удаление refresh token из cookie");
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
