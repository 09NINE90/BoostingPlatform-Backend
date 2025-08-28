package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.platform.LocalConstants.Variables.DEFAULT_USER_ROLE;
import static ru.platform.LocalConstants.Variables.DEFAULT_USER_TOKEN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту токена и роли пользователя")
public class AuthRsDto {

    @Schema(description = "Токен пользователя", example = DEFAULT_USER_TOKEN)
    private String token;

    @Schema(description = "Роль пользователя", example = DEFAULT_USER_ROLE)
    private String role;

}
