package ru.platform.user.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.games.dao.GameTag;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.notification.IMailService;
import ru.platform.user.dao.BoosterProfileEntity;
import ru.platform.user.dao.CustomerProfileEntity;
import ru.platform.user.dto.request.ConfirmPasswordRecoveryRqDto;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dao.UserProfileEntity;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.dto.response.MiniBoosterProfileRsDto;
import ru.platform.user.enumz.BoosterLevelName;
import ru.platform.user.enumz.CustomerStatus;
import ru.platform.user.repository.UserProfileRepository;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;
import ru.platform.user.service.IValidationUserService;
import ru.platform.utils.ConfirmationCodeUtil;
import ru.platform.utils.GenerateSecondIdUtil;
import ru.platform.utils.JwtUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.platform.LocalConstants.CustomerSettings.COUNT_ORDERS_FOR_IMMORTAL_STATUS;
import static ru.platform.LocalConstants.CustomerSettings.DISCOUNT_PERCENTAGE_FOR_EXPLORER_STATUS;
import static ru.platform.LocalConstants.Message.*;
import static ru.platform.LocalConstants.BoosterSettings.BOOSTER_LEGEND_TOTAL_INCOME;
import static ru.platform.LocalConstants.Variables.EMPTY_STRING;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.notification.MailType.PASSWORD_RECOVERY;
import static ru.platform.notification.MailType.REGISTRATION;
import static ru.platform.user.enumz.BoosterLevelName.*;
import static ru.platform.user.enumz.CustomerStatus.IMMORTAL;
import static ru.platform.user.enumz.CustomerStatus.VANGUARD;
import static ru.platform.user.enumz.UserRolesType.ROLE_CUSTOMER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final IValidationUserService validationUserService;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;
    private final IAuthService authService;
    private final IMailService mailService;
    private final JwtUtil jwtUtil;

    private final static String LOG_PREFIX = "UserService: {}";

    /**
     * Обработка запроса на регистрацию пользователя
     */
    @Override
    @PlatformMonitoring(name = MonitoringMethodType.REGISTRATION_USER)
    public ConfirmationRsDto registrationUser(SignupUserRqDto user) {
        validationUserService.validateSignUpUser(user);
        isUserExists(user);

        UserEntity userEntity = createUser(user);

        mailService.sendMail(userEntity, REGISTRATION);

        userRepository.save(userEntity);
        userProfileRepository.save(userEntity.getProfile());

        return new ConfirmationRsDto(CONFIRMATION_CODE_MASSAGE, userEntity.getUsername());
    }

    /**
     * Проверка существования пользователя в БД
     */
    private void isUserExists(SignupUserRqDto user) {
        boolean isExists = userRepository.existsByUsername(user.getEmail());
        if (isExists) throw new PlatformException(USER_EXISTS_ERROR);
    }

    /**
     * Создание сущности пользователя для сохранения в БД
     */
    private UserEntity createUser(SignupUserRqDto user) {
        String token = jwtUtil.generateConfirmationToken(user.getEmail(), user.getPassword());

        OffsetDateTime nowUtc = OffsetDateTime.now(ZoneOffset.UTC);
        UserEntity userEntity = UserEntity.builder()
                .username(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .roles(ROLE_CUSTOMER.name())
                .confirmationToken(token)
                .enabled(false)
                .build();
        UserProfileEntity profileEntity = UserProfileEntity.builder()
                .nickname(user.getNickname())
                .createdAt(nowUtc)
                .lastActivityAt(nowUtc)
                .secondId(randomId.getRandomId())
                .user(userEntity)
                .build();
        CustomerProfileEntity customerProfileEntity = CustomerProfileEntity.builder()
                .totalAmountOfOrders(BigDecimal.ZERO)
                .cashbackBalance(BigDecimal.ZERO)
                .status(CustomerStatus.EXPLORER)
                .discountPercentage(DISCOUNT_PERCENTAGE_FOR_EXPLORER_STATUS)
                .user(userEntity)
                .totalOrders(0)
                .build();

        userEntity.setProfile(profileEntity);
        userEntity.setCustomerProfile(customerProfileEntity);
        return userEntity;
    }

    /**
     * Проверка подтверждения регистрации
     */
    @Override
    @PlatformMonitoring(name = MonitoringMethodType.VERIFY_EMAIL)
    public Map<String, String> checkConfirmationSignUp(String confirmationToken, HttpServletResponse response) {
        String username = jwtUtil.extractUsername(confirmationToken);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        String password = jwtUtil.extractUserPassword(confirmationToken);

        if (user.getConfirmationToken().equals(confirmationToken)) {
            user.setEnabled(true);
            userRepository.save(user);
            return authService.trySignIn(new LoginUserRqDto(username, password), response);
        } else {
            log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getMessage());
            throw new PlatformException(EMAIL_VERIFIED_ERROR);
        }
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.PASSWORD_RESET_REQUEST)
    public void forgotPassword(ConfirmationEmailRqDto confirmation) {
        UserEntity user = userRepository.findByUsername(confirmation.getEmail())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        String confirmationCode = ConfirmationCodeUtil.generateConfirmationCode(6);

        user.setConfirmationCode(confirmationCode);
        userRepository.save(user);
        mailService.sendMail(user, PASSWORD_RECOVERY);
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.PASSWORD_RESET_VALIDATE)
    public void confirmPasswordRecovery(ConfirmPasswordRecoveryRqDto request) {
        UserEntity user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (!user.getConfirmationCode().equals(request.getCode())) {
            log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getMessage());
            throw new PlatformException(EMAIL_VERIFIED_ERROR);
        }
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.PASSWORD_CHANGE)
    public Map<String, String> changeUserPassword(ConfirmationEmailRqDto confirmation, HttpServletResponse response) {
        UserEntity user = userRepository.findByUsername(confirmation.getEmail())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        user.setPassword(encoder.encode(confirmation.getPassword()));
        userRepository.save(user);

        return authService.trySignIn(new LoginUserRqDto(
                confirmation.getEmail(),
                confirmation.getPassword()
        ), response);
    }

    @Override
    public ConfirmationRsDto resendConfirmationEmail(ConfirmationEmailRqDto confirmation) {
        UserEntity user = userRepository.findByUsername(confirmation.getEmail())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (user.isEnabled()) {
            throw new PlatformException(EMAIL_ALREADY_CONFIRMED_ERROR);
        }

        String newToken = jwtUtil.generateConfirmationToken(user.getUsername(), confirmation.getPassword());
        user.setConfirmationToken(newToken);
        userRepository.save(user);

        mailService.sendMail(user, REGISTRATION);

        return new ConfirmationRsDto(CONFIRMATION_CODE_MASSAGE);
    }

    /**
     * Запрос на изменение никнейма
     */
    @Override
    @Transactional
    @PlatformMonitoring(name = MonitoringMethodType.USER_CHANGE_NICKNAME)
    public void changeNickname(String nickname) {
        log.debug("Начало изменения никнейма на: {}", nickname);

        UserEntity userEntity = authService.getAuthUser();

        if (!userRepository.existsById(userEntity.getId())) {
            log.error("Пользователь с ID {} не найден", userEntity.getId());
            throw new PlatformException(NOT_FOUND_ERROR);
        }

        UserProfileEntity profile = userEntity.getProfile();

        userProfileRepository.updateNickname(profile.getId(), nickname);
        profile.setNickname(nickname);

        log.debug("Никнейм успешно изменен на: {}", nickname);
    }


    /**
     * Запрос на изменение описания профиля пользователя
     */
    @Override
    @Transactional
    @PlatformMonitoring(name = MonitoringMethodType.USER_CHANGE_DESCRIPTION)
    public void changeDescription(String description) {
        log.debug("Начало изменения описания профиля");

        UserEntity userEntity = authService.getAuthUser();

        if (!userRepository.existsById(userEntity.getId())) {
            log.error("Пользователь с ID {} не найден", userEntity.getId());
            throw new PlatformException(NOT_FOUND_ERROR);
        }

        UserProfileEntity profile = userEntity.getProfile();

        userProfileRepository.updateDescription(profile.getId(), description);
        profile.setDescription(description);

        log.debug("Описание профиля успешно обновлено");
    }

}
