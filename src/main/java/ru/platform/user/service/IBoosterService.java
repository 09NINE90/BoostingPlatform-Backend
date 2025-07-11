package ru.platform.user.service;

import ru.platform.finance.enumz.RecordType;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.MiniBoosterProfileRsDto;

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

    /**
     * Получение профиля пользователя (бустера)
     */
    BoosterProfileRsDto getBoosterProfileData();

    /**
     * Получение краткой информации о бустере
     */
    MiniBoosterProfileRsDto getBoosterMiniProfile(UUID boosterId);
}
