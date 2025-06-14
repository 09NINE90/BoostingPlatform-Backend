package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    List<OrderFromCartRsDto> createOrder(CreateOrderRqDto orderRqDto);
    List<OrderRsDto> getByCreator(OrderStatus status);
    OrderFiltersRsDto getOrderFilters();
    OrderListRsDto getAllOrders(OrdersByFiltersRqDto request);
    OrderRsDto getOrderById(UUID orderId);
    void acceptOrder(UUID orderId);
}
