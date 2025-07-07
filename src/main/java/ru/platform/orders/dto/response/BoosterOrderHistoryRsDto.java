package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;

import java.math.BigDecimal;

@Data
@Builder
public class BoosterOrderHistoryRsDto {

    @Schema(description = "Идентификатор заказа")
    private String id;

    @Schema(description = "Идентификатор заказа для отображения")
    private String orderId;

    @Schema(description = "Игра по которой заказ")
    private String gameName;

    @Schema(description = "Описание заказа")
    private String orderName;

    @Schema(description = "Дата и время, когда заказ стал выполненным")
    private String completedAt;

    @Schema(description = "Зарплата бустера за заказ")
    private BigDecimal salary;

    @Schema(description = "Статус заказа")
    private OrderStatus orderStatus;

}
