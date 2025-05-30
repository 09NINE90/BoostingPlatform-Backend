package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос для отправки кода подтверждения на проверку")
public class EmailConfirmationRequest {
    private String token;
}
