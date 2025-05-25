package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderRqDto {

    @ArraySchema(schema = @Schema(description = "Список элементов корзины, из которых будет сформирован заказ"))
    private List<CartItemDto> items;
}

