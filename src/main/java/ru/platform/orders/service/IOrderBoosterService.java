package ru.platform.orders.service;

import ru.platform.orders.dto.request.DashboardRqDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface IOrderBoosterService {
    DashboardFiltersRsDto getFiltersDashboard();
    OrderListRsDto getDashboard(DashboardRqDto request);
    void acceptOrder(UUID orderId);
    List<OrderByBoosterRsDto> getOrdersByBooster(OrdersByBoosterRqDto request);
    OrderFiltersRsDto getFiltersForOrdersByBooster();
}
