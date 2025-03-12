package ru.platform.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginUserRqDto {
    private String username;
    private String password;
}
