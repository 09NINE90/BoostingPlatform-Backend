package ru.platform.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.orders.dto.request.*;
import ru.platform.orders.dto.response.*;
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
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRqDto request) {
        orderCustomerService.createOrder(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getByCreator")
    @Operation(summary = "Получение списка заказов для пользователя")
    public ResponseEntity<List<OrderRsDto>> getOrdersByCreator(@RequestBody OrderByStatusRqDto request) {
        return ResponseEntity.ok(orderCustomerService.getOrdersByCreator(request.getStatus()));
    }

    @PostMapping("/getDashboard")
    @Operation(summary = "Получение списка заказов, которые бустер может взять")
    public ResponseEntity<OrderListRsDto> getDashboard(@RequestBody DashboardRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getDashboard(request));
    }

    @GetMapping("/getFiltersDashboard")
    @Operation(summary = "Получение значений фильтров для заказов")
    public ResponseEntity<DashboardFiltersRsDto> getFiltersDashboard() {
        return ResponseEntity.ok(orderBoosterService.getFiltersDashboard());
    }

    @PostMapping("/accept/{orderId}")
    @Operation(summary = "Взятие заказа в работу бустером")
    public ResponseEntity<Void> acceptOrder(@PathVariable("orderId") UUID orderId) {
        orderBoosterService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete/{orderId}")
    @Operation(summary = "Завершение работы над заказом")
    public ResponseEntity<Void> completeExecutionOrder(@PathVariable("orderId") UUID orderId) {
        orderBoosterService.completeExecutionOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/byBooster")
    @Operation(summary = "Получение списка заказов, закрепленных за бустером")
    public ResponseEntity<List<OrderByBoosterRsDto>> getOrdersByBooster(@RequestBody OrdersByBoosterRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getOrdersByBooster(request));
    }

    @GetMapping("/getFiltersForOrdersByBooster")
    @Operation(summary = "Получение значений фильтров для заказов, закрепленных за бустером")
    public ResponseEntity<OrderFiltersRsDto> getFiltersForOrdersByBooster() {
        return ResponseEntity.ok(orderBoosterService.getFiltersForOrdersByBooster());
    }

    @GetMapping("/boosterOrdersHistory")
    public ResponseEntity<List<BoosterOrderHistoryRsDto>> getBoosterOrdersHistory() {
        return ResponseEntity.ok(orderBoosterService.getBoosterOrdersHistory());
    }
}
