package ru.platform.user.service;

import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.dto.response.SignupRsDto;

public interface IUserService {
    SignupRsDto createUser(SignupUserRqDto user);
    AuthRsDto checkConfirmationSignUp(ConfirmationEmailRqDto confirmation);
}
