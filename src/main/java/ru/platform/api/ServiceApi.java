package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.ServicesEntity;
import ru.platform.request.ServicesRequest;
import ru.platform.response.ServicesResponse;
import ru.platform.service.IServiceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
public class ServiceApi {

    private final IServiceService service;

    @PostMapping("/getAllServices")
    @Schema(description = "Получение всех заказов, созданных админом")
    public ResponseEntity<ServicesResponse> getAllServices(@RequestBody ServicesRequest request){
        return ResponseEntity.ok(service.getAllServices(request));
    }

    @PostMapping("/saveEditingService")
    @Schema(description = "Изменение заказа, созданного админом")
    public ResponseEntity<Void> saveEditingService(@RequestBody ServicesEntity request){
        service.saveEditingService(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/addNewService")
    @Schema(description = "Создание заказа админом")
    public ResponseEntity<ServicesEntity> addNewService(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("selectedGameId") String selectedGameId,
            @RequestParam("categories") String categories,
            @RequestParam("image") MultipartFile imageFile,
            Authentication authentication) {
        return ResponseEntity.ok(service.addNewService(title, description, price, selectedGameId, categories, imageFile, authentication));
    }

    @DeleteMapping("/deleteService")
    @Schema(description = "Удаление заказа, созданного админом")
    public ResponseEntity<Void> deleteService(@RequestBody UUID id){
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}
