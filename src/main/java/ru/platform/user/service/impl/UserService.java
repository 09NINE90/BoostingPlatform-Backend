package ru.platform.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.user.mapper.IUserMapper;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dao.UserProfileEntity;
import ru.platform.user.dto.response.AuthResponse;
import ru.platform.user.repository.UserProfileRepository;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;

import static ru.platform.user.UserRolesType.ROLE_CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;
    private final IAuthService authService;
    private final IUserMapper userMapper;

    @Override
    public AuthResponse createUser(SignupUserRqDto user) {

        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .roles(ROLE_CUSTOMER.name())
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

        return authService.trySignup(userMapper.toLoginUserRqDto(user));
    }
}
