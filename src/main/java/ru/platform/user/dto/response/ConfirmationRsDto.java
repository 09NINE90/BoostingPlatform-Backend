package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import static ru.platform.LocalConstants.Message.CONFIRMATION_CODE_MASSAGE;
import static ru.platform.LocalConstants.Variables.DEFAULT_USER_MAIL;

@Data
@AllArgsConstructor
@Schema(description = "Подтверждение регистрации пользователя")
public class ConfirmationRsDto {

    @Schema(description = "Сообщение с подтвержением отправки письма на почту полльзователю", example = CONFIRMATION_CODE_MASSAGE)
    private String confirmation;

    @Schema(description = "Почта пользоавателя", example = DEFAULT_USER_MAIL)
    private String username;

    public ConfirmationRsDto(String confirmation) {
        this.confirmation = confirmation;
    }
}
