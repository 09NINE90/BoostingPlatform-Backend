package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.platform.orders.enumz.OrderStatus;

import java.util.List;

@Data
@Builder
@Jacksonized
public class OrderFromCartRsDto {

    @Schema(description = "Название заказа", example = "Legend of Eldoria")
    private String orderName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Текущий статус заказа", example = "NEW", enumAsRef = true)
    private OrderStatus orderStatus;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Выбранные дополнительные опции для заказа",
                    example = """
                [{
                  "optionTitle": "Стрим-сопровождение",
                  "value": "stream_support",
                  "label": "С включением стрима"
                }]"""
            ),
            schema = @Schema(implementation = CartSelectedOptionsDto.class)
    )
    private List<CartSelectedOptionsDto> selectedOptions;

    @Schema(description = "Общая стоимость заказа", example = "150.0")
    private double totalPrice;

    @Schema(description = "Общее время выполнения заказа (в часах)", example = "90")
    private int totalTime;

    @Data
    @Builder
    @Jacksonized
    @Schema(description = "Выбранная опция для заказа")
    public static class CartSelectedOptionsDto {

        @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
        private String optionTitle;

        @Schema(description = "Значение, переданное для опции", example = "stream_support")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
        private Object label;
    }

}

