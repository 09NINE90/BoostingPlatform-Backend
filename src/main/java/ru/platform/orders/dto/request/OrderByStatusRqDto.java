package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;

@Data
@Builder
public class OrderByStatusRqDto {

    @Schema(description = "Статус заказа", enumAsRef = true)
    private OrderStatus status;
}
