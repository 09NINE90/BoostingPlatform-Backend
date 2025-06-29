package ru.platform.finance.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.finance.service.IBoosterFinanceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/finance")
public class BoosterFinanceApi {

    private final IBoosterFinanceService boosterFinanceService;

    @PostMapping("/handleWithdrawal")
    public ResponseEntity<Void> handleWithdrawal(@RequestBody HandleWithdrawalRqDto request) {
        boosterFinanceService.handleWithdrawal(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balanceHistory")
    public ResponseEntity<List<BalanceHistoryRsDto>> getBalanceHistoryByBooster() {
        return ResponseEntity.ok(boosterFinanceService.getBalanceHistoryByBooster());
    }
}
