package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.platform.LocalConstants;

@Data
public class ConfirmPasswordRecoveryRqDto {

    @NotNull
    @Schema(description = "Email пользователя, восстанавливающего пароль", example = LocalConstants.Variables.DEFAULT_USER_MAIL)
    private String email;

    @NotNull
    @Schema(description = "Код подтверждения", example = "123456")
    private String code;
}
