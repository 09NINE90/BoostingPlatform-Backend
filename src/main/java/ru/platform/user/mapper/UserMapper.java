package ru.platform.user.mapper;

import org.springframework.stereotype.Component;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;

@Component
public class UserMapper implements IUserMapper {

    @Override
    public LoginUserRqDto toLoginUserRqDto(SignupUserRqDto user) {
        return  LoginUserRqDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public LoginUserRqDto toLoginUserRqDto(UserEntity user) {
        return  LoginUserRqDto.builder()
                .email(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
