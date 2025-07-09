package ru.platform.orders.service;

import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с заказами со стороны заказчика.
 */
public interface IOrderCustomerService {

    /**
     * Создаёт заказ на основе выбранных предметов.
     */
    void createOrder(List<UUID> itemsIds);

    /**
     * Получает список заказов, созданных пользователем, с возможностью фильтрации по статусу.
     */
    List<OrderRsDto> getOrdersByCreator(OrderStatus status);

    /**
     * Возвращает количество заказов, созданных пользователем.
     */
    long getCountOrdersByCustomer(UserEntity userEntity);

    /**
     * Возвращает количество выполненных заказов бустером.
     */
    long getCountCompletedOrdersByBooster(UserEntity userEntity);

    /**
     * Получает информацию о заказе по его идентификатору.
     */
    OrderRsDto getOrderById(UUID orderId);
}

