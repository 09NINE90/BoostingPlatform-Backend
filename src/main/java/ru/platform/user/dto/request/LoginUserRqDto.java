package ru.platform.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserRqDto {
    private String username;
    private String password;
}
