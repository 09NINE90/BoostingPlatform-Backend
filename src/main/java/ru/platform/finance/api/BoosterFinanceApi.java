package ru.platform.finance.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.finance.dto.request.HandleSendTipRqDto;
import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.finance.dto.response.OrderTipHistoryRsDto;
import ru.platform.finance.service.IBoosterFinanceService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.FINANCE_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.FINANCE_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boosters/finance")
@Tag(name = FINANCE_TAG_NAME, description = FINANCE_TAG_DESCRIPTION)
public class BoosterFinanceApi {

    private final IBoosterFinanceService boosterFinanceService;

    @PostMapping("/withdrawals")
    @Operation(summary = "Запросить вывод средств")
    public ResponseEntity<Void> handleWithdrawal(@RequestBody HandleWithdrawalRqDto request) {
        boosterFinanceService.handleWithdrawal(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tips")
    @Operation(summary = "Отправить чаевые")
    public ResponseEntity<Void> sendTip(@RequestBody HandleSendTipRqDto request) {
        boosterFinanceService.postHandleSendTip(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/history")
    @Operation(summary = "Получить историю баланса")
    public ResponseEntity<List<BalanceHistoryRsDto>> getBalanceHistory() {
        return ResponseEntity.ok(boosterFinanceService.getBalanceHistoryByBooster());
    }

    @GetMapping("/orders/{orderId}/tips")
    @Operation(summary = "Получить историю чаевых по заказу")
    public ResponseEntity<List<OrderTipHistoryRsDto>> getOrderTipHistory(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(boosterFinanceService.getOrderTipHistory(orderId));
    }
}
