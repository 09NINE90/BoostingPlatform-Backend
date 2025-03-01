package ru.platform.user.service;

import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.response.AuthResponse;

import java.util.List;

public interface IUserService {
    AuthResponse createUser(SignupUserRqDto user);
}
