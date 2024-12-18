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
import ru.platform.service.IOrdersService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApi {

    private final IOrdersService service;

    @PostMapping("/getAllOrders")
    @Schema(description = "Получение всех заказов, созданных админом")
    public ResponseEntity<ServicesResponse> getAllOrders(@RequestBody ServicesRequest request){
        return ResponseEntity.ok(service.getAllOrders(request));
    }

    @PostMapping("/saveEditingBaseOrder")
    @Schema(description = "Изменение заказа, созданного админом")
    public ResponseEntity<Void> saveEditingBaseOrder(@RequestBody ServicesEntity request){
        service.saveEditingBaseOrder(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/addNewOrder")
    @Schema(description = "Создание заказа админом")
    public ResponseEntity<ServicesEntity> addNewBaseOrder(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("selectedGameId") String selectedGameId,
            @RequestParam("categories") String categories,
            @RequestParam("image") MultipartFile imageFile,
            Authentication authentication) {

        return ResponseEntity.ok(service.addNewService(title, description, price, selectedGameId, categories, imageFile, authentication));
    }

    @DeleteMapping("/deleteBaseOrder")
    @Schema(description = "Удаление заказа, созданного админом")
    public ResponseEntity<Void> deleteBaseOrder(@RequestBody UUID id){
        service.deleteBaseOrder(id);
        return ResponseEntity.noContent().build();
    }

}
