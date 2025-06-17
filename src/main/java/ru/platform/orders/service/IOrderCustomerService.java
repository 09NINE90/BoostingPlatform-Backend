package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;

import java.util.List;

public interface IOrderCustomerService {
    List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto);
    List<OrderRsDto> getByCreator(OrderStatus status);
}
