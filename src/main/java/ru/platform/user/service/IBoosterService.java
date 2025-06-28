package ru.platform.user.service;

import ru.platform.finance.enumz.RecordType;
import ru.platform.user.dao.UserEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface IBoosterService {
    void updateBalance(UUID boosterId, BigDecimal amount, RecordType recordType);
    void checkBoosterBalance(UserEntity booster, BigDecimal withdrawalAmount);
}
