package ru.platform.user.mapper;

import org.springframework.stereotype.Component;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;

@Component
public class UserMapper implements IUserMapper {

    public LoginUserRqDto toLoginUserRqDto(SignupUserRqDto user) {
        return  LoginUserRqDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
