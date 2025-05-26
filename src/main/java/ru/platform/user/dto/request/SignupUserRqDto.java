package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.platform.LocalConstants.Variables.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для регистрации пользователя")
public class SignupUserRqDto {

    @Schema(description = "Никнейм пользователя", example = DEFAULT_USER_NICKNAME)
    private String nickname;

    @Schema(description = "Почта-логин пользователя", example = DEFAULT_USER_MAIL)
    private String email;

    @Schema(description = "Пароль пользователя", example = DEFAULT_USER_PASSWORD)
    private String password;
}
