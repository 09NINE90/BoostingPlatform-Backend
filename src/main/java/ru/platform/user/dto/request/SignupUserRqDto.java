package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static ru.platform.LocalConstants.Variables.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для регистрации пользователя")
public class SignupUserRqDto {

    @Schema(description = "Никнейм пользователя", requiredMode = REQUIRED, example = DEFAULT_USER_NICKNAME)
    @NotBlank(message = "Nickname is required")
    private String nickname;

    @Schema(description = "Почта-логин пользователя", requiredMode = REQUIRED, example = DEFAULT_USER_MAIL)
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Пароль пользователя", requiredMode = REQUIRED, example = DEFAULT_USER_PASSWORD)
    @NotBlank(message = "Password is required")
    private String password;

    @Schema(description = "UUID реферера", example = DEFAULT_UUID)
    private UUID refererId;
}
