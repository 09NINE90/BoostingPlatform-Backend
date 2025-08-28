package ru.platform.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.orders.dto.request.DashboardRqDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.response.*;
import ru.platform.orders.service.IOrderBoosterService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.BOOSTER_ORDER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.BOOSTER_ORDER_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/booster")
@Tag(name = BOOSTER_ORDER_TAG_NAME, description = BOOSTER_ORDER_TAG_DESCRIPTION)
public class BoosterOrderApi {

    private final IOrderBoosterService orderBoosterService;

    @PostMapping("/dashboard")
    @Operation(summary = "Получить доступные заказы для бустера (дашборд)")
    public ResponseEntity<OrderListRsDto> getDashboard(@RequestBody DashboardRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getDashboard(request));
    }

    @GetMapping("/dashboard/filters")
    @Operation(summary = "Получить фильтры для дашборда бустера")
    public ResponseEntity<DashboardFiltersRsDto> getFiltersDashboard() {
        return ResponseEntity.ok(orderBoosterService.getFiltersDashboard());
    }

    @PostMapping("/accept/{orderId}")
    @Operation(summary = "Принять заказ в работу")
    public ResponseEntity<Void> acceptOrder(@PathVariable("orderId") UUID orderId) {
        orderBoosterService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete/{orderId}")
    @Operation(summary = "Завершить выполнение заказа")
    public ResponseEntity<Void> completeExecutionOrder(@PathVariable("orderId") UUID orderId) {
        orderBoosterService.completeExecutionOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Получить заказ (для бустера)")
    public ResponseEntity<OrderByBoosterRsDto> getBoosterOrderById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderBoosterService.getOrderById(orderId));
    }

    @PostMapping("/my-orders")
    @Operation(summary = "Получить заказы текущего бустера")
    public ResponseEntity<List<OrderByBoosterRsDto>> getOrdersByBooster(@RequestBody OrdersByBoosterRqDto request) {
        return ResponseEntity.ok(orderBoosterService.getOrdersByBooster(request));
    }

    @GetMapping("/my-orders/filters")
    @Operation(summary = "Получить фильтры для заказов бустера")
    public ResponseEntity<OrderFiltersRsDto> getFiltersForOrdersByBooster() {
        return ResponseEntity.ok(orderBoosterService.getFiltersForOrdersByBooster());
    }

    @GetMapping("/history")
    @Operation(summary = "Получить историю заказов бустера")
    public ResponseEntity<List<BoosterOrderHistoryRsDto>> getBoosterOrdersHistory() {
        return ResponseEntity.ok(orderBoosterService.getBoosterOrdersHistory());
    }
}
