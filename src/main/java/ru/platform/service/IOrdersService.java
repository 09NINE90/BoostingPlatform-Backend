package ru.platform.service;

import org.springframework.security.core.Authentication;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;

import java.util.UUID;

public interface IOrdersService {
    BaseOrderResponse getAllOrders(BaseOrderRequest request);
    void saveEditingBaseOrder(BaseOrdersEntity request);
    BaseOrdersEntity addNewBaseOrder(BaseOrderRequest request, Authentication authentication);
    void deleteBaseOrder(UUID id);
}
