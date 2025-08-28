package ru.platform.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;

import java.util.Map;

/**
 * Сервис для обработки аутентификации и управления токенами.
 */
public interface IAuthService {

    /**
     * Выполняет попытку входа пользователя в систему и возвращает токен и роль пользователя.
     */
    Map<String, String> trySignIn(LoginUserRqDto userRqDto, HttpServletResponse response);

    /**
     * Возвращает текущего аутентифицированного пользователя.
     */
    UserEntity getAuthUser();

    /**
     * Обновляет access-токен на основе refresh-токена.
     */
    Map<String, String> refreshAccessToken(HttpServletRequest request, HttpServletResponse response);

    /**
     * Выполняет выход пользователя из системы и удаляет токены.
     */
    void logout(HttpServletRequest request, HttpServletResponse response);
}

