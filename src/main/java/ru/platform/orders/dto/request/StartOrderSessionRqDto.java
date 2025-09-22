package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Объект для создания сессии бустером")
public class StartOrderSessionRqDto {

    @Schema(description = "Продолжительность сессии (в часах)", requiredMode = REQUIRED, example = "3")
    @Min(value = 1, message = "Duration must be greater than or equal to {value}")
    private int duration;

    @Schema(description = "Ссылка на стрим", requiredMode = NOT_REQUIRED, example = "https://example.com/stream")
    private String streamLink;

}
