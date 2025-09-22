package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Объект для создания сессии бустером")
public class FinishOrderSessionRqDto {

    @Schema(description = "Описание выполненной работы во время сессии", requiredMode = REQUIRED, example = "Upgraded 2 weapons and upgraded 3 levels")
    @NotNull(message = "Progress message must be not null")
    @NotBlank(message = "Progress message must be not empty")
    private String progressMessage;

    @Schema(description = "Ссылка на imgur для отслеживания прогресса", requiredMode = REQUIRED, example = "https://example.com/imgur")
    @NotNull(message = "Imgur link must be not null")
    @NotBlank(message = "Imgur link must be not empty")
    private String imgurLink;

}
