package ru.platform.offers.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectedOptionToCartDto {

    @Schema(description = "Название выбранной опции", example = "Коллекция достижений")
    private String optionTitle;

    @Schema(description = "Значение выбранной опции", example = "achievement_pack_01")
    private Object value;

    @Schema(description = "Метка, отображаемая пользователю", example = "Пак достижений #1")
    private Object label;
}

