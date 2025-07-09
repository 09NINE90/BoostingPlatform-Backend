package ru.platform.user.service;

import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;

public interface IValidationUserService {

    void validateSignUpUser(SignupUserRqDto userDto);

    void validateSignInUser(LoginUserRqDto userDto);
}
