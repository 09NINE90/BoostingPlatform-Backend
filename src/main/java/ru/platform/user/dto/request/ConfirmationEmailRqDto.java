package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static ru.platform.LocalConstants.Variables.DEFAULT_USER_MAIL;
import static ru.platform.LocalConstants.Variables.DEFAULT_USER_PASSWORD;

@Data
@Schema(description = "Запрос для отправки кода подтверждения на проверку")
public class ConfirmationEmailRqDto {

    @Schema(description = "Почта пользователя для подтверждения", example = DEFAULT_USER_MAIL)
    private String email;
    @Schema(description = "Пароль пользователя", example = DEFAULT_USER_PASSWORD)
    private String password;

}
