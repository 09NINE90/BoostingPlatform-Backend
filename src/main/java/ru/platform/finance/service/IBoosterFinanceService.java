package ru.platform.finance.service;

import ru.platform.finance.dto.request.HandleSendTipRqDto;
import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.finance.dto.response.OrderTipHistoryRsDto;
import ru.platform.orders.dao.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface IBoosterFinanceService {
    void createNewRecordOfSalaryBooster(OrderEntity order);
    void handleWithdrawal(HandleWithdrawalRqDto request);
    List<BalanceHistoryRsDto> getBalanceHistoryByBooster();
    void postHandleSendTip(HandleSendTipRqDto request);
    List<OrderTipHistoryRsDto> getOrderTipHistory(UUID orderId);
}
