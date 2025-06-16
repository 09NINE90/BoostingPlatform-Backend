package ru.platform.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.annotation.RoleRequired;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrderByStatusRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.service.IOrderService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.ORDER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.ORDER_TAG_NAME;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = ORDER_TAG_NAME, description = ORDER_TAG_DESCRIPTION)
public class OrderApi {

    private final IOrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Создание заказа")
    public ResponseEntity<List<OrderFromCartRsDto>> createOrder(@RequestBody CreateOrderRqDto request) {
       return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PostMapping("/getByCreator")
    @Operation(summary = "Получение списка заказов для пользователя")
    public ResponseEntity<List<OrderRsDto>> getByCreator(@RequestBody OrderByStatusRqDto request) {
        return ResponseEntity.ok(orderService.getByCreator(request.getStatus()));
    }

    @PostMapping("/getAll")
    public ResponseEntity<OrderListRsDto> getAllOrders(@RequestBody OrdersByFiltersRqDto request) {
        return ResponseEntity.ok(orderService.getAllOrders(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRsDto> getOrderById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/getFilters")
    public ResponseEntity<OrderFiltersRsDto> getOrderFilters() {
        return ResponseEntity.ok(orderService.getOrderFilters());
    }

    @PostMapping("/accept/{orderId}")
    @RoleRequired(value = "ROLE_BOOSTER")
    public ResponseEntity<Void> acceptOrder(@PathVariable("orderId") UUID orderId) {
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/byBooster")
    public ResponseEntity<List<OrderRsDto>> getOrdersByBooster(@RequestBody OrdersByFiltersRqDto request) {
        return ResponseEntity.ok(orderService.getOrdersByBooster(request));
    }
}
