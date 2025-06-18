package ru.platform.orders.service;

import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderByBoosterRsDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;

import java.util.List;
import java.util.UUID;

public interface IOrderBoosterService {
    OrderFiltersRsDto getFiltersForCreatedOrders();
    OrderListRsDto getAllOrders(OrdersByFiltersRqDto request);
    void acceptOrder(UUID orderId);
    List<OrderByBoosterRsDto> getOrdersByBooster(OrdersByBoosterRqDto request);
    OrderFiltersRsDto getFiltersForOrdersByBooster();
}
