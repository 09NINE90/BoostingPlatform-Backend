package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту токена и роли пользователя")
public class AuthRsDto {

    @Schema(description = "Токен пользователя")
    private String token;
    @Schema(description = "Роль пользователя", example = "ROLE_CUSTOMER")
    private String role;

}
