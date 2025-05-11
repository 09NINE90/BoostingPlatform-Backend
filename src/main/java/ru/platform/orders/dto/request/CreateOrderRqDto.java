package ru.platform.orders.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderRqDto {

    List<CartItemDto> items;
}
