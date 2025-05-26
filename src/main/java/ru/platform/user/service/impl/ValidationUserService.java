package ru.platform.user.service.impl;

import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.service.IValidationUserService;
import ru.platform.utils.DtoUtil;

import static ru.platform.exception.ErrorType.NOT_VALID_REQUEST;

@Service
public class ValidationUserService implements IValidationUserService {

    @Override
    public void validateSignUpUser(SignupUserRqDto userDto) {
        if (DtoUtil.isDeepEmpty(userDto, SignupUserRqDto::getNickname) ||
                DtoUtil.isDeepEmpty(userDto, SignupUserRqDto::getEmail) ||
                DtoUtil.isDeepEmpty(userDto, SignupUserRqDto::getPassword)) {
            throw new PlatformException(NOT_VALID_REQUEST);
        }
    }

    @Override
    public void validateSignInUser(LoginUserRqDto userDto) {
        if (DtoUtil.isDeepEmpty(userDto, LoginUserRqDto::getEmail) ||
                DtoUtil.isDeepEmpty(userDto, LoginUserRqDto::getPassword)) {
            throw new PlatformException(NOT_VALID_REQUEST);
        }
    }
}
