package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IOrdersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApi {

    private final IOrdersService service;

    @PostMapping("/getAllOrders")
    public ResponseEntity<BaseOrderResponse> getAllOrders(@RequestBody BaseOrderRequest request){
        return ResponseEntity.ok(service.getAllOrders(request));
    }

    @PostMapping("/saveEditingBaseOrder")
    public void saveEditingBaseOrder(@RequestBody BaseOrdersEntity request){
        service.saveEditingBaseOrder(request);
    }

}
