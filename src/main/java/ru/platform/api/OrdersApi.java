package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IOrdersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApi {

    private final IOrdersService service;

    @GetMapping("/getAllOrders")
    public ResponseEntity<BaseOrderResponse> getAllOrders(){
        return ResponseEntity.ok(service.getAllOrders(BaseOrderRequest.builder().build()));
    }
}
