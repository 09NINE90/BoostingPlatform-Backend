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
import ru.platform.utils.GenerateSecondIdUtil;
import ru.platform.utils.JwtUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.platform.LocalConstants.Message.*;
import static ru.platform.LocalConstants.BoosterSettings.BOOSTER_LEGEND_TOTAL_INCOME;
import static ru.platform.LocalConstants.Variables.EMPTY_STRING;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.notification.MailType.PASSWORD_RECOVERY;
import static ru.platform.notification.MailType.REGISTRATION;
import static ru.platform.user.enumz.BoosterLevelName.*;
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
    @PlatformMonitoring(name = MonitoringMethodType.CREATION_USER)
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
                .discountPercentage(1)
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
    public ConfirmationRsDto forgotPassword(ConfirmationEmailRqDto confirmation) {
        UserEntity user = userRepository.findByUsername(confirmation.getEmail())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        String confirmationToken = jwtUtil.generateConfirmationToken(user.getUsername(), EMPTY_STRING);

        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);
        mailService.sendMail(user, PASSWORD_RECOVERY);

        return new ConfirmationRsDto(CONFIRMATION_CODE_MASSAGE);
    }

    @Override
    public ConfirmationRsDto confirmPasswordRecovery(String confirmationToken) {
        String username = jwtUtil.extractUsername(confirmationToken);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (user.getConfirmationToken().equals(confirmationToken)) {
            return new ConfirmationRsDto(SUCCESS_PASSWORD_RECOVERY, username);
        } else {
            log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getMessage());
            throw new PlatformException(EMAIL_VERIFIED_ERROR);
        }
    }

    @Override
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
     * Получение информации о заказчике
     */
    @Override
    public CustomerProfileRsDto getCustomerProfileData() {
        UserEntity userEntity = authService.getAuthUser();
        UserProfileEntity profileEntity = userEntity.getProfile();
        CustomerProfileEntity customerProfile = userEntity.getCustomerProfile();

        return CustomerProfileRsDto.builder()
                .email(userEntity.getUsername())
                .nickname(profileEntity.getNickname())
                .imageUrl(profileEntity.getImageUrl())
                .secondId(profileEntity.getSecondId())
                .description(profileEntity.getDescription())
                .discountPercentage(customerProfile.getDiscountPercentage())
                .cashbackBalance(customerProfile.getCashbackBalance())
                .status(customerProfile.getStatus())
                .totalOrders(customerProfile.getTotalOrders())
                .build();
    }

    /**
     * Получение информации о бустере
     */
    @Override
    public BoosterProfileRsDto getBoosterProfileData() {
        UserEntity userEntity = authService.getAuthUser();
        UserProfileEntity profileEntity = userEntity.getProfile();
        BoosterProfileEntity boosterProfile = userEntity.getBoosterProfile();

        return BoosterProfileRsDto.builder()
                .email(userEntity.getUsername())
                .nickname(profileEntity.getNickname())
                .imageUrl(profileEntity.getImageUrl())
                .secondId(profileEntity.getSecondId())
                .description(profileEntity.getDescription())
                .level(boosterProfile.getLevel())
                .nextLevel(getNextLevel(boosterProfile.getLevel()))
                .percentageOfOrder((double) Math.round(boosterProfile.getPercentageOfOrder() * 100))
                .balance(boosterProfile.getBalance())
                .totalIncome(boosterProfile.getTotalIncome())
                .numberOfCompletedOrders(boosterProfile.getNumberOfCompletedOrders())
                .progressAccountStatus(boosterProfile.getTotalIncome().multiply(BigDecimal.valueOf(100))
                        .divide(BOOSTER_LEGEND_TOTAL_INCOME, 2, RoundingMode.HALF_UP))
                .totalTips(boosterProfile.getTotalTips())
                .gameTags(getGameTags(boosterProfile.getGameTags()))
                .build();
    }

    /**
     * Получение следующего уровня бустера
     */
    private BoosterLevelName getNextLevel(BoosterLevelName level) {
        return switch (level) {
            case ROOKIE -> VETERAN;
            case VETERAN -> ELITE;
            case ELITE -> LEGEND;
            case LEGEND -> null;
        };
    }

    /**
     * Получение игровых тегов для фронта
     */
    private List<BoosterProfileRsDto.GameTag> getGameTags(List<GameTag> gameTags) {
        if (gameTags == null || gameTags.isEmpty()) return null;
        return gameTags.stream()
                .map(gameTag ->
                        BoosterProfileRsDto.GameTag.builder()
                                .id(gameTag.getId().toString())
                                .name(gameTag.getGame().getTitle())
                                .build()
                )
                .toList();
    }

    /**
     * Запрос на изменение никнейма
     */
    @Override
    @Transactional
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

    /**
     * Получение краткой информации о бустере
     */
    @Override
    public MiniBoosterProfileRsDto getBoosterMiniProfile(UUID boosterId) {
        UserEntity booster = userRepository.findById(boosterId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        return MiniBoosterProfileRsDto.builder()
                .boosterName(booster.getProfile().getNickname())
                .boosterDescription(booster.getProfile().getDescription())
                .avatarUrl(booster.getProfile().getImageUrl())
                .boosterLevel(booster.getBoosterProfile().getLevel())
                .numberOfCompletedOrders(booster.getBoosterProfile().getNumberOfCompletedOrders())
                .build();
    }
}
