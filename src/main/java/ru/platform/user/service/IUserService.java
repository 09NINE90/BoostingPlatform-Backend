package ru.platform.user.service;

import jakarta.servlet.http.HttpServletResponse;
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
    ConfirmationRsDto forgotPassword(ConfirmationEmailRqDto confirmation);

    /**
     * Подтверждение смены пароля по токену восстановления
     */
    ConfirmationRsDto confirmPasswordRecovery(String confirmationToken);

    /**
     * Смена пароля пользователя
     */
    Map<String, String> changeUserPassword(ConfirmationEmailRqDto confirmation, HttpServletResponse response);

    /**
     * Повторная отправка письма для подтверждения email
     */
    ConfirmationRsDto resendConfirmationEmail(ConfirmationEmailRqDto email);

    /**
     * Получение профиля пользователя (заказчика)
     */
    CustomerProfileRsDto getCustomerProfileData();

    /**
     * Обновление никнейма пользователя
     */
    void changeNickname(String nickname);

    /**
     * Получение профиля пользователя (бустера)
     */
    BoosterProfileRsDto getBoosterProfileData();

    /**
     * Обновление описания профиля пользователя
     */
    void changeDescription(String description);

    /**
     * Получение краткой информации о бустере
     */
    MiniBoosterProfileRsDto getBoosterMiniProfile(UUID boosterId);
}