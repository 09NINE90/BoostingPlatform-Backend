package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusRsDto {

    private String id;

    @Schema(description = "Статус заказа", enumAsRef = true)
    private String name;

}
