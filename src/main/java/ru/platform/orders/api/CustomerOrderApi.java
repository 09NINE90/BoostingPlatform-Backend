package ru.platform.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.orders.dto.request.*;
import ru.platform.orders.dto.response.*;
import ru.platform.orders.service.IOrderCustomerService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.CUSTOMER_ORDER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.CUSTOMER_ORDER_TAG_NAME;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/customer")
@Tag(name = CUSTOMER_ORDER_TAG_NAME, description = CUSTOMER_ORDER_TAG_DESCRIPTION)
public class CustomerOrderApi {

    private final IOrderCustomerService orderCustomerService;

    @PostMapping("/create")
    @Operation(summary = "Создать новый заказ")
    public ResponseEntity<Void> createOrder(@RequestBody List<UUID> itemsIds) {
        orderCustomerService.createOrder(itemsIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/my-orders")
    @Operation(summary = "Получить заказы текущего пользователя")
    public ResponseEntity<List<OrderRsDto>> getOrdersByCreator(@RequestBody OrderByStatusRqDto request) {
        return ResponseEntity.ok(orderCustomerService.getOrdersByCreator(request.getStatus()));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Получить заказ (для клиента)")
    public ResponseEntity<OrderRsDto> getCustomerOrderById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderCustomerService.getOrderById(orderId));
    }

}
