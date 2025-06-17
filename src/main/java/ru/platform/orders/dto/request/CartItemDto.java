package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@Jacksonized
public class CartItemDto {

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private UUID offerId;

    @Schema(description = "Название предложения", example = "Coop fight")
    private String offerName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Название платформы", example = "XBOX")
    private String gamePlatform;

    @Schema(description = "Базовая цена без учёта опций", example = "120.0")
    private Double basePrice;

    @ArraySchema(schema = @Schema(description = "Список выбранных опций для предложения"))
    private List<CartSelectedOptionsDto> selectedOptions;

    @Schema(description = "Общая стоимость с учётом опций", example = "150.0")
    private Double totalPrice;

    @Schema(description = "Общее время выполнения (в часах)", example = "90")
    private Integer totalTime;

    @Data
    @Builder
    @Jacksonized
    public static class CartSelectedOptionsDto {

        @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
        private String optionTitle;

        @Schema(description = "Значение, переданное для опции", example = "stream_support")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
        private Object label;
    }
}

