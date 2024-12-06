package ru.platform.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;

import java.util.UUID;

public interface IOrdersService {
    BaseOrderResponse getAllOrders(BaseOrderRequest request);
    void saveEditingBaseOrder(BaseOrdersEntity request);
    void deleteBaseOrder(UUID id);
    BaseOrdersEntity addNewBaseOrder(String title, String description, String price, String selectedGameId, String categories, MultipartFile imageFile, Authentication authentication);
}
