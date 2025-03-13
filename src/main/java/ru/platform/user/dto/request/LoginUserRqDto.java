package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Объект для авторизации пользователя")
public class LoginUserRqDto {

    @Schema(description = "Почта-логин пользователя", example = "myemail@mail.com")
    private String username;
    @Schema(description = "Пароль пользователя", example = "Qaz123!")
    private String password;

}
