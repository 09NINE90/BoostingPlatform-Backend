package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static ru.platform.LocalConstants.Variables.DEFAULT_USER_MAIL;
import static ru.platform.LocalConstants.Variables.DEFAULT_USER_PASSWORD;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Объект для авторизации пользователя")
public class LoginUserRqDto {

    @Schema(description = "Почта-логин пользователя", example = DEFAULT_USER_MAIL)
    private String username;
    @Schema(description = "Пароль пользователя", example = DEFAULT_USER_PASSWORD)
    private String password;

}
