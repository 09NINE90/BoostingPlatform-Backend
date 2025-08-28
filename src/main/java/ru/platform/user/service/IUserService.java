package ru.platform.user.service;

import jakarta.servlet.http.HttpServletResponse;
import ru.platform.user.dto.request.ConfirmPasswordRecoveryRqDto;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.dto.response.MiniBoosterProfileRsDto;

import java.util.Map;
import java.util.UUID;

public interface IUserService {
    /**
     * Регистрация нового пользователя в системе
     */
    ConfirmationRsDto registrationUser(SignupUserRqDto user);

    /**
     * Подтверждение регистрации пользователя по полученному токену
     */
    Map<String, String> checkConfirmationSignUp(String confirmationToken, HttpServletResponse response);

    /**
     * Инициирование процесса восстановления пароля
     */
    void forgotPassword(ConfirmationEmailRqDto confirmation);

    /**
     * Подтверждение смены пароля по токену восстановления
     */
    void confirmPasswordRecovery(ConfirmPasswordRecoveryRqDto request);

    /**
     * Смена пароля пользователя
     */
    Map<String, String> changeUserPassword(ConfirmationEmailRqDto confirmation, HttpServletResponse response);

    /**
     * Повторная отправка письма для подтверждения email
     */
    ConfirmationRsDto resendConfirmationEmail(ConfirmationEmailRqDto email);

    /**
     * Обновление никнейма пользователя
     */
    void changeNickname(String nickname);

    /**
     * Обновление описания профиля пользователя
     */
    void changeDescription(String description);
}