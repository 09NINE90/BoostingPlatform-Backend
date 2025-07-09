package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.offers.enumz.OfferOptionType;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferOptionRsDto {

    @Schema(description = "Идентификатор опции", example = DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private String optionId;

    @Schema(description = "Название опции", example = "Region")
    private String title;

    @Schema(description = "Тип опции", example = "BUTTONS", enumAsRef = true)
    private OfferOptionType type;

    @Schema(description = "Флаг множественного выбора", example = "false")
    private boolean multiple;

    private Double sliderPriceChange;

    @Schema(description = "Минимальное значение (для SLIDER)", example = "1")
    private Integer min;

    @Schema(description = "Максимальное значение (для SLIDER)", example = "100")
    private Integer max;

    @Schema(description = "Шаг изменения (для SLIDER)", example = "5")
    private Integer step;

    @ArraySchema(schema = @Schema(description = "Список элементов опции"))
    private List<OptionItemRsDto> items;

}
