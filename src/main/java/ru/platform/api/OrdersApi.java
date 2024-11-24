package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderEditRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IOrdersService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApi {

    private final IOrdersService service;

    @PostMapping("/getAllOrders")
    @Schema(description = "Получение всех заказов, созданных админом")
    public ResponseEntity<BaseOrderResponse> getAllOrders(@RequestBody BaseOrderEditRequest request){
        return ResponseEntity.ok(service.getAllOrders(request));
    }

    @PostMapping("/saveEditingBaseOrder")
    @Schema(description = "Изменение заказа, созданного админом")
    public void saveEditingBaseOrder(@RequestBody BaseOrdersEntity request){
        service.saveEditingBaseOrder(request);
    }

    @PostMapping("/addNewOrder")
    @Schema(description = "Создание заказа админом")
    public void addNewBaseOrder(@RequestBody BaseOrderEditRequest request, Authentication authentication){
        service.addNewBaseOrder(request, authentication);
    }

    @DeleteMapping("/deleteBaseOrder")
    @Schema(description = "Удаление заказа, созданного админом")
    public void deleteBaseOrder(@RequestBody UUID id){
        service.deleteBaseOrder(id);
    }

}
