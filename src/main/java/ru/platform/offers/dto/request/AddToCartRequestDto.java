package ru.platform.offers.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.games.enumz.GamePlatform;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Schema(description = "Добавление заказа в корзину")
public class AddToCartRequestDto {

    @Schema(description = "Идентификатор предложения, которое добавляется в корзину", example = DEFAULT_UUID)
    private UUID offerId;

    @Schema(description = "Идентификатор игры", example = DEFAULT_UUID)
    private String gameId;

    @Schema(description = "Название платформы", example = "XBOX", enumAsRef = true)
    private GamePlatform gamePlatform;

    @Schema(description = "Базовая стоимость предложения без опций", example = "120.0")
    private BigDecimal basePrice;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Выбранные дополнительные опции",
                    example = """
                [{
                  "optionTitle": "Коллекция достижений",
                  "value": "achievement_pack_01",
                  "label": "Пак достижений #1"
                }]"""
            ),
            schema = @Schema(implementation = SelectedOptionToCartDto.class)
    )
    private List<SelectedOptionToCartDto> selectedOptions;

    @Schema(description = "Общая стоимость с учётом выбранных опций", example = "150.0")
    private BigDecimal totalPrice;

    @Schema(description = "Общее время выполнения услуги (в часах)", example = "120")
    private int totalTime;

    @Data
    @Builder
    @Schema(description = "Выбранная опция для корзины")
    public static class SelectedOptionToCartDto {

        @Schema(description = "Название выбранной опции", example = "Коллекция достижений")
        private String optionTitle;

        @Schema(description = "Значение выбранной опции", example = "achievement_pack_01")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "Пак достижений #1")
        private Object label;
    }
}
