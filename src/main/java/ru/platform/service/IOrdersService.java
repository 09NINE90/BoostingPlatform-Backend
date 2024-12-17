package ru.platform.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.ServicesEntity;
import ru.platform.request.ServicesRequest;
import ru.platform.response.ServicesResponse;

import java.util.UUID;

public interface IOrdersService {
    ServicesResponse getAllOrders(ServicesRequest request);
    void saveEditingBaseOrder(ServicesEntity request);
    void deleteBaseOrder(UUID id);
    ServicesEntity addNewService(String title, String description, String price, String selectedGameId, String categories, MultipartFile imageFile, Authentication authentication);
}
