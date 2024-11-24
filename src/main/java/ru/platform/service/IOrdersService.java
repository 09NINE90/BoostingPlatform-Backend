package ru.platform.service;

import org.springframework.security.core.Authentication;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderEditRequest;
import ru.platform.response.BaseOrderResponse;

import java.util.UUID;

public interface IOrdersService {
    BaseOrderResponse getAllOrders(BaseOrderEditRequest request);
    void saveEditingBaseOrder(BaseOrdersEntity request);
    void addNewBaseOrder(BaseOrderEditRequest request, Authentication authentication);
    void deleteBaseOrder(UUID id);
}
