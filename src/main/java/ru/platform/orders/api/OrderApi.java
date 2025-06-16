package ru.platform.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.annotation.RoleRequired;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.request.OrderByStatusRqDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderFiltersRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.service.IOrderBoosterService;
import ru.platform.orders.service.IOrderCustomerService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.ORDER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.ORDER_TAG_NAME;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = ORDER_TAG_NAME, description = ORDER_TAG_DESCRIPTION)
public class OrderApi {

    private final IOrderCustomerService orderCustomerService;
    private final IOrderBoosterService orderBoosterService;

    @PostMapping("/create")
    @Operation(summary = "Создание заказа")
    public ResponseEntity<List<OrderFromCartRsDto>> createOrder(@RequestBody CreateOrderRqDto request) {
       return ResponseEntity.ok(orderCustomerService.createOrder(request));
    }

    @PostMapping("/getByCreator")
    @Operation(summary = "Получение списка заказов для пользователя")
    public ResponseEntity<List<OrderRsDto>> getByCreator(@RequestBody OrderByStatusRqDto request) {
        return ResponseEntity.ok(orderCustomerService.getByCreator(request.getStatus()));
    }

    @PostMapping("/getAll")
    @Operation(summary = "Получение списка заказов, которые бустер может взять")
    public ResponseEntity<OrderListRsDto> getAllOrders(@RequestBody OrdersByFiltersRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getAllOrders(request));
    }

    @GetMapping("/getFiltersForCreatedOrders")
    @Operation(summary = "Получение значений фильтров для заказов")
    public ResponseEntity<OrderFiltersRsDto> getFiltersForCreatedOrders() {
        return ResponseEntity.ok(orderBoosterService.getFiltersForCreatedOrders());
    }

    @PostMapping("/accept/{orderId}")
    @RoleRequired(value = "ROLE_BOOSTER")
    @Operation(summary = "Взятие заказа в работу бустером")
    public ResponseEntity<Void> acceptOrder(@PathVariable("orderId") UUID orderId) {
        orderBoosterService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/byBooster")
    @RoleRequired(value = "ROLE_BOOSTER")
    @Operation(summary = "Получение списка заказов, закрепленных за бустером")
    public ResponseEntity<List<OrderRsDto>> getOrdersByBooster(@RequestBody OrdersByBoosterRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getOrdersByBooster(request));
    }

    @GetMapping("/getFiltersForOrdersByBooster")
    @RoleRequired(value = "ROLE_BOOSTER")
    @Operation(summary = "Получение значений фильтров для заказов, закрепленных за бустером")
    public ResponseEntity<OrderFiltersRsDto> getFiltersForOrdersByBooster() {
        return ResponseEntity.ok(orderBoosterService.getFiltersForOrdersByBooster());
    }
}
