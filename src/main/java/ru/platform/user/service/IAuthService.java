package ru.platform.user.service;

import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;

public interface IAuthService {
    AuthRsDto trySignIn(LoginUserRqDto userRqDto);
    UserEntity getAuthUser();
}
