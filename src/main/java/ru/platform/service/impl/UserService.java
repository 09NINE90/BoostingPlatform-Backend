package ru.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.dto.UserDTO;
import ru.platform.entity.UserEntity;
import ru.platform.entity.UserProfileEntity;
import ru.platform.entity.enums.ERoles;
import ru.platform.repository.UserProfileRepository;
import ru.platform.repository.UserRepository;
import ru.platform.service.IUserService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserDTO user) {

        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .roles(ERoles.CUSTOMER.getTitle())
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

        return userEntity;
    }
}
