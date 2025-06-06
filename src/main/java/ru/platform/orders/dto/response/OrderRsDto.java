package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRsDto {

    @Schema(description = "Идентификатор заказа", example = "Legend of Eldoria")
    private String orderId;

    @Schema(description = "Идентификатор заказа", example = "Legend of Eldoria")
    private String secondId;

    @Schema(description = "Название заказа", example = "Legend of Eldoria")
    private String offerName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Название платформы", example = "XBOX")
    private String gamePlatform;

    @Schema(description = "Текущий статус заказа", example = "NEW", enumAsRef = true)
    private String orderStatus;

    @Schema(description = "Общая стоимость заказа", example = "150.0")
    private double totalPrice;

    @ArraySchema(schema = @Schema(description = "Список опций, выбранных для заказа"))
    private List<CartSelectedOptionsDto> selectedOptions;

    @Data
    @Builder
    public static class CartSelectedOptionsDto {

        @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
        private String optionTitle;

        @Schema(description = "Значение, переданное для опции", example = "stream_support")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
        private Object label;
    }
}
