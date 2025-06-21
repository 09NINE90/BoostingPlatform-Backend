package ru.platform.user.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.dto.response.CustomerProfileRsDto;

import java.util.Map;

public interface IUserService {
    @Schema(description = "Создание пользователя")
    ConfirmationRsDto registrationUser(SignupUserRqDto user);

    @Schema(description = "Подтверждение регистрации пользователя")
    Map<String, String> checkConfirmationSignUp(String confirmationToken, HttpServletResponse response);

    @Schema(description = "Запрос кода для восстановления пароля")
    ConfirmationRsDto forgotPassword(ConfirmationEmailRqDto confirmation);

    @Schema(description = "Подтверждение кода для смены пароля")
    ConfirmationRsDto confirmPasswordRecovery(String confirmationToken);

    @Schema(description = "Запрос на смену пароля")
    Map<String, String> changeUserPassword(ConfirmationEmailRqDto confirmation, HttpServletResponse response);

    @Schema(description = "Запрос на повторное письмо подтверждения почты")
    ConfirmationRsDto resendConfirmationEmail(ConfirmationEmailRqDto email);

    @Schema(description = "Запрос на получение данных профиля заказчика")
    CustomerProfileRsDto getCustomerProfileData();

    @Schema(description = "Запрос на смену никнейма пользователя")
    void changeNickname(String nickname);

    @Schema(description = "Запрос на получение данных профиля бустера")
    BoosterProfileRsDto getBoosterProfileData();
}
