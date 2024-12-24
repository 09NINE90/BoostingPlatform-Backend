package ru.platform.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.ServicesEntity;
import ru.platform.request.ServicesRequest;
import ru.platform.response.ServicesResponse;

import java.util.UUID;

public interface IServiceService {
    ServicesResponse getAllServices(ServicesRequest request);
    void saveEditingService(ServicesEntity request);
    void deleteService(UUID id);
    ServicesEntity addNewService(ServicesRequest request, MultipartFile imageFile, Authentication authentication);
}
