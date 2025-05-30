package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO элемента опции предложения")
public class OptionItemRsDto {

    @Schema(description = "Идентификатор элемента опции", example = DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Внутреннее значение элемента (используется в бизнес-логике)", example = "EU")
    private String value;

    @Schema(description = "Отображаемое название элемента (видимое пользователю)", example = "Europe")
    private String label;

    @Schema(description = "Изменение цены при выборе этого элемента", example = "10.50")
    private Double priceChange;

    @Schema(description = "Изменение времени выполнения в часах при выборе этого элемента", example = "2")
    private Integer timeChange;

    @Schema(description = "Процентное изменение цены (если применяется)", example = "15.0")
    private Double percentChange;

    @ArraySchema(schema = @Schema(description = "Список вложенных под-опций, доступных при выборе этого элемента"))
    private List<OfferOptionRsDto> subOptions;
}
