package ru.platform.user.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.notification.IMailService;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dao.UserProfileEntity;
import ru.platform.user.dto.response.UserProfileRsDto;
import ru.platform.user.repository.UserProfileRepository;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;
import ru.platform.user.service.IValidationUserService;
import ru.platform.utils.GenerateSecondIdUtil;
import ru.platform.utils.JwtUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static ru.platform.LocalConstants.Message.*;
import static ru.platform.LocalConstants.Variables.EMPTY_STRING;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.notification.MailType.PASSWORD_RECOVERY;
import static ru.platform.notification.MailType.REGISTRATION;
import static ru.platform.user.UserRolesType.ROLE_CUSTOMER;

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

        userEntity.setProfile(profileEntity);
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

    @Override
    public UserProfileRsDto getUserProfileData() {
        UserEntity userEntity = authService.getAuthUser();
        UserProfileEntity profileEntity = userEntity.getProfile();
        return UserProfileRsDto.builder()
                .nickname(profileEntity.getNickname())
                .imageUrl(profileEntity.getImageUrl())
                .secondId(profileEntity.getSecondId())
                .build();
    }

    @Override
    @Transactional
    public void changeNickname(String nickname) {
        UserEntity userEntity = authService.getAuthUser();
        userEntity = userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        UserProfileEntity profileEntity = userEntity.getProfile();
        if (profileEntity == null) {
            profileEntity = new UserProfileEntity();
            userEntity.setProfile(profileEntity);
        }
        profileEntity.setNickname(nickname);
        userRepository.save(userEntity);
        userProfileRepository.save(userEntity.getProfile());
    }

}
