package ru.platform.user.service;

import ru.platform.finance.enumz.RecordType;

import java.math.BigDecimal;
import java.util.UUID;

public interface IBoosterService {
    void updateBalance(UUID boosterId, BigDecimal amount, RecordType recordType);
}
