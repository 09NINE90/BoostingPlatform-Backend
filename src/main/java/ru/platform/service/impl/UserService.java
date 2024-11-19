package ru.platform.service.impl;

import org.springframework.stereotype.Service;
import ru.platform.repository.UserRepository;
import ru.platform.service.IUserService;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
