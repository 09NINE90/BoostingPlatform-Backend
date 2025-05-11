package ru.platform.offers.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
public class OfferToCartRqDto {

    @Schema(description = "Идентификатор предложения, которое добавляется в корзину", example = DEFAULT_UUID)
    private UUID offerId;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Базовая стоимость предложения без опций", example = "120.0")
    private double basePrice;

    @ArraySchema(schema = @Schema(description = "Список выбранных опций для добавления в корзину"))
    private List<SelectedOptionToCartDto> selectedOptions;

    @Schema(description = "Общая стоимость с учётом выбранных опций", example = "150.0")
    private double totalPrice;

    @Schema(description = "Общее время выполнения услуги (в минутах)", example = "120")
    private int totalTime;
}
