package ru.platform.user.service;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.dto.response.ConfirmationRsDto;

public interface IUserService {
    @Schema(description = "Создание пользователя")
    ConfirmationRsDto registrationUser(SignupUserRqDto user);

    @Schema(description = "Подтверждение регистрации пользователя")
    AuthRsDto checkConfirmationSignUp(String confirmationToken);

    @Schema(description = "Запрос кода для восстановления пароля")
    ConfirmationRsDto forgotPassword(ConfirmationEmailRqDto confirmation);

    @Schema(description = "Подтверждение кода для смены пароля")
    ConfirmationRsDto confirmPasswordRecovery(String confirmationToken);

    @Schema(description = "Запрос на смену пароля")
    AuthRsDto changeUserPassword(ConfirmationEmailRqDto confirmation);

    @Schema(description = "Запрос на повторное письмо подтверждения почты")
    ConfirmationRsDto resendConfirmationEmail(ConfirmationEmailRqDto email);
}
