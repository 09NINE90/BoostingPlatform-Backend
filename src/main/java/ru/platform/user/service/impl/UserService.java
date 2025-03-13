package ru.platform.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.notification.IMailService;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dao.UserProfileEntity;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.repository.UserProfileRepository;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static ru.platform.LocalConstants.Message.*;
import static ru.platform.exception.ErrorType.EMAIL_VERIFIED_ERROR;
import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;
import static ru.platform.notification.MailType.PASSWORD_RECOVERY;
import static ru.platform.notification.MailType.REGISTRATION;
import static ru.platform.user.UserRolesType.ROLE_CUSTOMER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;
    private final IAuthService authService;
    private final IMailService mailService;

    private final static String LOG_PREFIX = "UserService: {}";

    @Override
    public ConfirmationRsDto createUser(SignupUserRqDto user) {
        String confirmationCode = generateConfirmationCode();

        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .roles(ROLE_CUSTOMER.name())
                .confirmationCode(confirmationCode)
                .enabled(false)
                .build();
        UserProfileEntity profileEntity = UserProfileEntity.builder()
                .nickname(user.getNickname())
                .createdAt(LocalDate.now())
                .lastActivityAt(LocalDate.now())
                .secondId(randomId.getRandomId())
                .user(userEntity)
                .build();

        userEntity.setProfile(profileEntity);

        userRepository.save(userEntity);
        userProfileRepository.save(profileEntity);

        mailService.sendMail(userEntity, REGISTRATION);

        return new ConfirmationRsDto(CONFIRMATION_CODE_MASSAGE);
    }

    private String generateConfirmationCode() {
        return String.valueOf(new Random().nextInt(999999));
    }

    @Override
    public AuthRsDto checkConfirmationSignUp(ConfirmationEmailRqDto confirmation) {
        Optional<UserEntity> user = userRepository.findByUsername(confirmation.getEmail());
        if (user.isPresent()) {
            String code = confirmation.getConfirmationCode();
            if (user.get().getConfirmationCode().equals(code)) {
                user.get().setEnabled(true);
                userRepository.save(user.get());
                return authService.trySignup(new LoginUserRqDto(
                        confirmation.getEmail(),
                        confirmation.getPassword()
                ));
            } else {
                log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getDescription());
                throw new PlatformException(EMAIL_VERIFIED_ERROR);
            }
        } else {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getDescription());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public ConfirmationRsDto forgotPassword(ConfirmationEmailRqDto confirmation) {
        Optional<UserEntity> user = userRepository.findByUsername(confirmation.getEmail());

        if (user.isPresent()) {
            String confirmationCode = generateConfirmationCode();

            user.get().setConfirmationCode(confirmationCode);
            userRepository.save(user.get());
            mailService.sendMail(user.orElse(null), PASSWORD_RECOVERY);

            return new ConfirmationRsDto(CONFIRMATION_CODE_MASSAGE);
        } else {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getDescription());
            throw new PlatformException(NOT_FOUND_ERROR);
        }

    }

    @Override
    public ConfirmationRsDto confirmPasswordRecovery(ConfirmationEmailRqDto confirmation) {
        Optional<UserEntity> user = userRepository.findByUsername(confirmation.getEmail());
        if (user.isPresent()) {
            String code = confirmation.getConfirmationCode();

            if (user.get().getConfirmationCode().equals(code)) {
                return new ConfirmationRsDto(SUCCESS_PASSWORD_RECOVERY);
            } else {
                log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getDescription());
                throw new PlatformException(EMAIL_VERIFIED_ERROR);
            }
        } else {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getDescription());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public AuthRsDto changeUserPassword(ConfirmationEmailRqDto confirmation) {
        Optional<UserEntity> user = userRepository.findByUsername(confirmation.getEmail());

        if (user.isPresent()) {
            user.get().setPassword(encoder.encode(confirmation.getPassword()));
            userRepository.save(user.get());

            return authService.trySignup(new LoginUserRqDto(
                    confirmation.getEmail(),
                    confirmation.getPassword()
            ));
        } else {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getDescription());
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

}
