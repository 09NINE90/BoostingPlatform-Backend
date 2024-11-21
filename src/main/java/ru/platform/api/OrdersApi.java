package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.service.IOrdersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApi {

    private final IOrdersService service;

    @GetMapping("/getAllOrders")
    public List<BaseOrdersEntity> getAllOrders(){
        return service.getAllOrders();
    }
}
