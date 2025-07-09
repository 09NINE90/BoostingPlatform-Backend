package ru.platform.orders.service;

import ru.platform.orders.dto.request.DashboardRqDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.response.*;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с заказами бустеров.
 */
public interface IOrderBoosterService {

    /**
     * Получает фильтры для отображения дашборда заказов.
     */
    DashboardFiltersRsDto getFiltersDashboard();

    /**
     * Получает список заказов для отображения на дашборде согласно фильтрам.
     */
    OrderListRsDto getDashboard(DashboardRqDto request);

    /**
     * Получает список заказов, закреплённых за конкретным бустером.
     */
    List<OrderByBoosterRsDto> getOrdersByBooster(OrdersByBoosterRqDto request);

    /**
     * Получает доступные фильтры для отображения заказов по бустеру.
     */
    OrderFiltersRsDto getFiltersForOrdersByBooster();

    /**
     * Принимает заказ для выполнения бустером.
     */
    void acceptOrder(UUID orderId);

    /**
     * Завершает выполнение заказа бустером.
     */
    void completeExecutionOrder(UUID orderId);

    /**
     * Получает историю заказов, выполненных бустером.
     */
    List<BoosterOrderHistoryRsDto> getBoosterOrdersHistory();

    /**
     * Получает информацию о заказе по его идентификатору.
     */
    OrderByBoosterRsDto getOrderById(UUID orderId);
}
