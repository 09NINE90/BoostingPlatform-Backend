package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartSelectedOptionsDto {

    @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
    private String optionTitle;

    @Schema(description = "Значение, переданное для опции", example = "stream_support")
    private Object value;

    @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
    private Object label;
}
