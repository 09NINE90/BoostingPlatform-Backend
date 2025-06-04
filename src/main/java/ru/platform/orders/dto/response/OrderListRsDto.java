package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderListRsDto {

    @Schema(description = "Идентификатор заказа", example = "Legend of Eldoria")
    private String orderId;

    @Schema(description = "Название заказа", example = "Legend of Eldoria")
    private String offerName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Текущий статус заказа", example = "NEW", enumAsRef = true)
    private String orderStatus;

    @Schema(description = "Общая стоимость заказа", example = "150.0")
    private double totalPrice;

}
