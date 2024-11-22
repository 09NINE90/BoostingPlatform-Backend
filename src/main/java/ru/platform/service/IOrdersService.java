package ru.platform.service;

import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;

import java.util.List;

public interface IOrdersService {
    BaseOrderResponse getAllOrders(BaseOrderRequest request);
}
