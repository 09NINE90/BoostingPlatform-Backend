package ru.platform.user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.notification.IMailService;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.SignupRsDto;
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
import java.util.Random;

import static ru.platform.notification.MailType.REGISTRATION;
import static ru.platform.user.UserRolesType.ROLE_CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;
    private final IAuthService authService;
    private final IMailService mailService;

    @Override
    public SignupRsDto createUser(SignupUserRqDto user) {
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

        return new SignupRsDto("Confirmation code sent to email");
    }

    private String generateConfirmationCode() {
        return String.valueOf(new Random().nextInt(999999));
    }

    @Override
    public AuthRsDto checkConfirmationSignUp(ConfirmationEmailRqDto confirmation) {
        try {
            UserEntity user = userRepository.findByUsername(confirmation.getEmail());
            String code = confirmation.getConfirmationCode();

            if (user.getConfirmationCode().equals(code)) {
                user.setEnabled(true);
                userRepository.save(user);
                return authService.trySignup(new LoginUserRqDto(
                        confirmation.getEmail(),
                        confirmation.getPassword()
                ));
            }
        }catch (Exception e) {
            throw new EntityNotFoundException("User not found");
        }

        return null;
    }

}
