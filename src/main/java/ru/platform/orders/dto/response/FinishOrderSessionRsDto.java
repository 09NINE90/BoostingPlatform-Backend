package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Объект для завершения сессии бустером")
public class FinishOrderSessionRsDto {

    @Schema(description = "Заявленная продолжительность (в часах)", requiredMode = REQUIRED, example = "3")
    @Min(value = 1, message = "Duration must be greater than or equal to {value}")
    private int duration;

    @Schema(description = "Ссылка на стрим", requiredMode = NOT_REQUIRED, example = "https://example.com/stream")
    private String streamLink;

    @Schema(description = "Фактическая продолжительность сессии", requiredMode = REQUIRED, example = "1 hour 1 minute")
    private String factDuration;

    @Schema(description = "Описание выполненной работы во время сессии", requiredMode = REQUIRED, example = "Upgraded 2 weapons and upgraded 3 levels")
    private String progressMessage;

    @Schema(description = "Ссылка на imgur для отслеживания прогресса", requiredMode = REQUIRED, example = "https://example.com/imgur")
    private String imgurLink;

}
