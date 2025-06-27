package ru.platform.finance.service;

import ru.platform.orders.dao.OrderEntity;

public interface IBoosterFinanceService {
    void createNewRecordOfSalaryBooster(OrderEntity order);
}
