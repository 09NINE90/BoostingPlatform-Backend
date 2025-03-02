package ru.platform.user.service;

import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.response.AuthResponse;

public interface IUserService {
    AuthResponse createUser(SignupUserRqDto user);
}
