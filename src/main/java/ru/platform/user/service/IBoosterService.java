package ru.platform.user.service;

import ru.platform.finance.enumz.RecordType;
import ru.platform.user.dao.UserEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface IBoosterService {
    /**
     * Увеличение баланса бустера на указанную сумму
     */
    void updateBalance(UUID boosterId, BigDecimal amount, RecordType recordType);

    /**
     * Проверяет, достаточно ли средств на балансе бустера для выполнения операции.
     */
    void checkBoosterBalance(UserEntity booster, BigDecimal withdrawalAmount);
}
