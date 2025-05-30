package ru.platform.user.mapper;

import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;

public interface IUserMapper {

    LoginUserRqDto toLoginUserRqDto(SignupUserRqDto user);
    LoginUserRqDto toLoginUserRqDto(UserEntity user);

}
