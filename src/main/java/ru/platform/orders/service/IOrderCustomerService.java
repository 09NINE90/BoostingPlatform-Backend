package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.dao.UserEntity;

import java.util.List;

public interface IOrderCustomerService {
    void createOrder(CreateOrderRqDto orderRqDto);
    List<OrderRsDto> getByCreator(OrderStatus status);
    long getCountOrdersByCustomer(UserEntity userEntity);
    long getCountCompletedOrdersByBooster(UserEntity userEntity);
}
