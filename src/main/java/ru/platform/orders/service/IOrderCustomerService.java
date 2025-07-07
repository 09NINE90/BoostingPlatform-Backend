package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IOrderCustomerService {
    void createOrder(CreateOrderRqDto orderRqDto);
    List<OrderRsDto> getOrdersByCreator(OrderStatus status);
    long getCountOrdersByCustomer(UserEntity userEntity);
    long getCountCompletedOrdersByBooster(UserEntity userEntity);
    OrderRsDto getOrderById(UUID orderId);
}
