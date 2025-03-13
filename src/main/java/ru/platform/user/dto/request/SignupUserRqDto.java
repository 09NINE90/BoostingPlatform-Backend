package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для регистрации пользователя")
public class SignupUserRqDto {

    @Schema(description = "Никнейм пользователя", example = "myNickname")
    private String nickname;
    @Schema(description = "Почта-логин пользователя", example = "myemail@mail.com")
    private String username;
    @Schema(description = "Пароль пользователя", example = "Qaz123!")
    private String password;
    @Schema(description = "Роль пользователя", example = "ROLE_CUSTOMER")
    private String roles;

}
