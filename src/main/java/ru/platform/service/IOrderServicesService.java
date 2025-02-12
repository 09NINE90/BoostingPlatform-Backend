package ru.platform.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.OrderServicesEntity;
import ru.platform.request.CreateOrderServicesRequest;
import ru.platform.request.OrderServicesRequest;
import ru.platform.response.OrderServicesResponse;

import java.util.UUID;

public interface IOrderServicesService {
    OrderServicesResponse getAllServices(OrderServicesRequest request);
    void saveEditingService(OrderServicesEntity request);
    void deleteService(UUID id);
    OrderServicesEntity addNewService(OrderServicesRequest request, MultipartFile imageFile, Authentication authentication);
    OrderServicesEntity addNewService(CreateOrderServicesRequest request, Authentication authentication);

}
