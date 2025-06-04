package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.enumz.OrderStatus;

import java.util.List;

public interface IOrderService {
    List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto);
    List<OrderListRsDto> getByCreator(OrderStatus status);
    OrderFiltersRsDto getOrderFilters();
}
