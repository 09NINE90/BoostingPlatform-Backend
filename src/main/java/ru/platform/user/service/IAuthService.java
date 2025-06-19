package ru.platform.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;

import java.util.Map;

public interface IAuthService {
    Map<String, String> trySignIn(LoginUserRqDto userRqDto, HttpServletResponse response);
    UserEntity getAuthUser();
    Map<String, String> refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
