package ru.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.platform.user.dto.detail.CustomUserDetails;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.repository.UserRepository;

import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(foundUser.orElse(null));
    }
}
