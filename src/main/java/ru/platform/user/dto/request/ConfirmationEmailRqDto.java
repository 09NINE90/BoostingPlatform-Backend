package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос для отправки кода подтверждения на проверку")
public class ConfirmationEmailRqDto {

    @Schema(description = "Почта пользователя для подтверждения", example = "myemail@mail.com")
    private String email;
    @Schema(description = "Пароль пользователя", example = "Qaz123!")
    private String password;
    @Schema(description = "Код подтверждения", example = "123456")
    private String confirmationCode;

}
