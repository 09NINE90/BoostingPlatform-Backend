package ru.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platform.dto.UserDTO;
import ru.platform.entity.UserEntity;
import ru.platform.entity.enums.ERoles;
import ru.platform.repository.UserRepository;
import ru.platform.service.IUserService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final GenerateSecondIdUtil randomId;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserDTO user) {
        return userRepository.save(UserEntity.builder()
                .username(user.getUsername())
                .roles(ERoles.CUSTOMER.getTitle())
                .password(encoder.encode(user.getPassword()))
                .nickname(user.getNickname())
                .createdAt(LocalDate.now())
                .ordersCount(0)
                .secondId(randomId.getRandomId())
                .build());
    }
}
