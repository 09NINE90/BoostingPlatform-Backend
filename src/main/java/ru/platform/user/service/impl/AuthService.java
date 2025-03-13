package ru.platform.user.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.repository.UserRepository;
import ru.platform.user.service.IAuthService;
import ru.platform.utils.JwtUtil;

import java.util.Optional;

import static ru.platform.exception.ErrorType.*;
import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final static String LOG_PREFIX = "AuthService: {}, Email: {}";

    @Override
    public AuthRsDto trySignup(LoginUserRqDto userRqDto) {
        checkConfirmationEmail(userRqDto);
        try {
            return userAuthorization(userRqDto);
        }catch (Exception e) {
            log.error(LOG_PREFIX, UNAUTHORIZED_ERROR.getDescription(), userRqDto.getUsername());
            throw new PlatformException(UNAUTHORIZED_ERROR);
        }
    }

    private void checkConfirmationEmail(LoginUserRqDto userRqDto){
        Optional<UserEntity> user = userRepository.findByUsername(userRqDto.getUsername());
        if(user.isPresent()){
            if (!user.get().isEnabled()){
                log.error(LOG_PREFIX, EMAIL_VERIFIED_ERROR.getDescription(), userRqDto.getUsername());
                throw new PlatformException(EMAIL_VERIFIED_ERROR);
            }
        } else {
            log.error(LOG_PREFIX, NOT_FOUND_ERROR.getDescription(), userRqDto.getUsername());
            throw new PlatformException(NOT_FOUND_ERROR);
        }

    }

    private AuthRsDto userAuthorization(LoginUserRqDto userRqDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRqDto.getUsername(), userRqDto.getPassword())
        );
        String role = getRole(authentication);
        String token = jwtUtil.generateToken(userRqDto.getUsername(), role);
        log.info("User {} signed up", userRqDto.getUsername());
        return new AuthRsDto(token, role);
    }

    private String getRole(Authentication authentication){
        return authentication
                .getAuthorities()
                .toString()
                .replace("[", "")
                .replace("]", "");
    }

    public static String checkToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
