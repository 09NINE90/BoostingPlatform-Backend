package ru.platform.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupUserRqDto {
    private String nickname;
    private String username;
    private String password;
    private String roles;

}
