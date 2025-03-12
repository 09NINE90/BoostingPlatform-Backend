package ru.platform.user.service;

import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;

public interface IAuthService {
    AuthRsDto trySignup(LoginUserRqDto userRqDto);
}
