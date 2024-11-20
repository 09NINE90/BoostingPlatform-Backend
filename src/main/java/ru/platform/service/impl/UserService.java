package ru.platform.service.impl;

import org.springframework.stereotype.Service;
import ru.platform.entity.UserEntity;
import ru.platform.repository.UserRepository;
import ru.platform.service.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
