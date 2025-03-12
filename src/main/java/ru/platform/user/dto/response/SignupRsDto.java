package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Подтверждение регистрации пользователя")
public class SignupRsDto {

    @Schema(description = "Сообщение с подтвержением отправки письма на посту полльзователю")
    private String confirmation;

}
