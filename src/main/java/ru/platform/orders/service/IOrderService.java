package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrdersByCreatorRqDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;

import java.util.List;

public interface IOrderService {
    List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto);
    List<OrderFromCartRsDto> getByCreator(OrdersByCreatorRqDto ordersByCreatorRqDto);
}
