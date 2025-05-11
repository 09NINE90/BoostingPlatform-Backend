package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.offers.dto.request.SelectedOptionToCartDto;

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

    @ArraySchema(schema = @Schema(description = "Список выбранных опций для предложения"))
    private List<SelectedOptionToCartDto> selectedOptions;

    @Schema(description = "Общая стоимость с учётом выбранных опций", example = "150.0")
    private double totalPrice;

    @Schema(description = "Общее время выполнения услуги (в минутах)", example = "120")
    private int totalTime;
}

