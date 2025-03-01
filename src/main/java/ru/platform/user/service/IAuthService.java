package ru.platform.user.service;

import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse trySignup(LoginUserRqDto userRqDto);
}
