package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.OrderServicesEntity;
import ru.platform.request.CreateOrderServicesRequest;
import ru.platform.request.OrderServicesRequest;
import ru.platform.response.OrderServicesResponse;
import ru.platform.service.IOrderServicesService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
public class OrderServicesApi {

    private final IOrderServicesService service;

    @PostMapping("/getAllServices")
    @Schema(
            description = "Получение всех заказов, созданных админом"
    )
    public ResponseEntity<OrderServicesResponse> getAllServices(@RequestBody OrderServicesRequest request){
        return ResponseEntity.ok(service.getAllServices(request));
    }

    @PostMapping("/saveEditingService")
    @Schema(
            description = "Изменение заказа, созданного админом"
    )
    public ResponseEntity<Void> saveEditingService(@RequestBody OrderServicesEntity request){
        service.saveEditingService(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addNewService")
    @Schema(
            description = "Создание заказа админом"
    )
    public ResponseEntity<OrderServicesEntity> addNewService(
            @RequestPart("file") MultipartFile file,
            @RequestPart("services") OrderServicesRequest services,
            Authentication authentication) {
        return ResponseEntity.ok(service.addNewService(services, file, authentication));
    }

    @DeleteMapping("/deleteService")
    @Schema(
            description = "Удаление заказа, созданного админом"
    )
    public ResponseEntity<Void> deleteService(@RequestBody UUID id){
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}
