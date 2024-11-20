package ru.platform.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.platform.dto.CustomUserDetails;
import ru.platform.entity.UserEntity;
import ru.platform.repository.UserRepository;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUser = userRepository.findByUsername(username);
        if (foundUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(foundUser);
    }
}
