package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
public class OfferCartRsDto {

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private UUID offerId;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @ArraySchema(schema = @Schema(description = "Список выбранных опций для предложения"), minItems = 1, maxItems = 100)
    @Size(min = 1, max = 100)
    private List<SelectedOptionToCartDto> selectedOptions;

    @Schema(description = "Общая стоимость с учётом выбранных опций", example = "150.0")
    private double totalPrice;

    @Schema(description = "Общее время выполнения услуги (в минутах)", example = "120")
    private int totalTime;

    @Data
    @Builder
    public static class SelectedOptionToCartDto {

        @Schema(description = "Название выбранной опции", example = "Коллекция достижений")
        private String optionTitle;

        @Schema(description = "Значение выбранной опции", example = "achievement_pack_01")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "Пак достижений #1")
        private Object label;
    }
}

