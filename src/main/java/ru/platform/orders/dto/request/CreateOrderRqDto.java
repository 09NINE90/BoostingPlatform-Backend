package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CreateOrderRqDto {

    @NonNull
    @ArraySchema(schema = @Schema(description = "Список элементов корзины, из которых будет сформирован заказ"))
    private List<CartItemDto> items;
}

