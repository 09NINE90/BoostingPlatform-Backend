package ru.platform.finance.service;

import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.orders.dao.OrderEntity;

import java.util.List;

public interface IBoosterFinanceService {
    void createNewRecordOfSalaryBooster(OrderEntity order);
    void handleWithdrawal(HandleWithdrawalRqDto request);
    List<BalanceHistoryRsDto> getBalanceHistoryByBooster();
}
