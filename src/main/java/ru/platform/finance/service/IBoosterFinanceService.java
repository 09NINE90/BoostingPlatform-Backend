package ru.platform.finance.service;

import ru.platform.finance.dto.request.HandleSendTipRqDto;
import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.finance.dto.response.OrderTipHistoryRsDto;
import ru.platform.orders.dao.OrderEntity;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления финансовыми операциями, связанными с бустерами.
 */
public interface IBoosterFinanceService {

    /**
     * Создаёт новую запись о начислении зарплаты бустеру на основе заказа.
     */
    void createNewRecordOfSalaryBooster(OrderEntity order);

    /**
     * Обрабатывает запрос на вывод средств бустером.
     */
    void handleWithdrawal(HandleWithdrawalRqDto request);

    /**
     * Возвращает историю баланса бустера.
     */
    List<BalanceHistoryRsDto> getBalanceHistoryByBooster();

    /**
     * Обрабатывает отправку чаевых бустеру.
     */
    void postHandleSendTip(HandleSendTipRqDto request);

    /**
     * Возвращает историю чаевых для указанного заказа.
     */
    List<OrderTipHistoryRsDto> getOrderTipHistory(UUID orderId);
}

