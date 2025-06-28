package ru.platform.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.PlatformException;
import ru.platform.finance.enumz.RecordType;
import ru.platform.user.dao.BoosterProfileEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.repository.BoosterProfileRepository;
import ru.platform.user.service.IBoosterService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.platform.LocalConstants.BoosterSettings.*;
import static ru.platform.exception.ErrorType.*;
import static ru.platform.exception.ErrorType.WITHDRAWAL_AMOUNT_MORE_THEN_BALANCE_ERROR;
import static ru.platform.user.enumz.BoosterLevelName.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoosterService implements IBoosterService {

    private final BoosterProfileRepository boosterProfileRepository;

    @Override
    @Transactional
    public void updateBalance(UUID boosterId, BigDecimal amount, RecordType recordType) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            log.error("Сумма не может быть равна нулю");
            throw new PlatformException(ZERO_AMOUNT_ERROR);
        }

        log.debug("Поиск объекта бустера для начисления баланса");
        BoosterProfileEntity profile = boosterProfileRepository.findByIdForUpdate(boosterId)
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        switch (recordType) {
            case TIP -> updateTips(amount, profile);
            case SALARY -> updateIncome(amount, profile);
        }

        log.debug("Сохранение бустера с обновленным балансом");
        boosterProfileRepository.save(profile);
    }

    /**
     * Обновление данных о чаевых бустера
     */
    private void updateTips(BigDecimal amount, BoosterProfileEntity profile) {
        log.debug("Обновление данных о чаевых бустера");
        profile.setBalance(profile.getBalance().add(amount));
        profile.setTotalTips(profile.getTotalTips().add(amount));
    }

    /**
     * Обновление данных о зарплате бустера
     */
    private void updateIncome(BigDecimal amount, BoosterProfileEntity profile) {
        log.debug("Обновление данных о зарплате бустера");
        BigDecimal newBalance = profile.getBalance().add(amount);
        BigDecimal newTotalIncome = profile.getTotalIncome().add(amount);
        int newOrdersCount = profile.getNumberOfCompletedOrders() + 1;

        profile.setBalance(newBalance);
        profile.setTotalIncome(newTotalIncome);
        profile.setNumberOfCompletedOrders(newOrdersCount);

        checkAndUpdateLevel(profile, newTotalIncome, newOrdersCount);
    }

    /**
     * Проверка обновления уровня бустера
     */
    private void checkAndUpdateLevel(BoosterProfileEntity profile,
                                     BigDecimal newTotalIncome,
                                     int newOrdersCount) {

        if (newTotalIncome.compareTo(BOOSTER_LEGEND_TOTAL_INCOME) >= 0 ||
                newOrdersCount >= LEGEND_MIN_ORDERS) {
            profile.setLevel(LEGEND);
            profile.setPercentageOfOrder(BOOSTER_LEGEND_PERCENT);
            log.debug("Бустер ID: {} повышен до уровня LEGEND", profile.getId());

        } else if (newTotalIncome.compareTo(BOOSTER_ELITE_TOTAL_INCOME) >= 0 ||
                newOrdersCount >= ELITE_MIN_ORDERS) {
            profile.setLevel(ELITE);
            profile.setPercentageOfOrder(BOOSTER_ELITE_PERCENT);
            log.debug("Бустер ID: {} повышен до уровня ELITE", profile.getId());

        } else if (newTotalIncome.compareTo(BOOSTER_VETERAN_TOTAL_INCOME) >= 0 ||
                newOrdersCount >= VETERAN_MIN_ORDERS) {
            profile.setLevel(VETERAN);
            profile.setPercentageOfOrder(BOOSTER_VETERAN_PERCENT);
            log.debug("Бустер ID: {} повышен до уровня VETERAN", profile.getId());
        }
    }

    @Override
    @Transactional
    public void checkBoosterBalance(UserEntity booster, BigDecimal withdrawalAmount) {
        if (withdrawalAmount.compareTo(MINIMUM_WITHDRAWAL_AMOUNT) < 0) {
            log.error("Введенная сумма для вывода средств меньше {}$", MINIMUM_WITHDRAWAL_AMOUNT);
            throw new PlatformException(WITHDRAWAL_AMOUNT_LESS_THEN_MINIMUM_ERROR);
        }

        BoosterProfileEntity boosterProfile = boosterProfileRepository.findByIdForUpdate(booster.getBoosterProfile().getId())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (boosterProfile.getBalance().compareTo(withdrawalAmount) < 0) {
            log.error("Введенная сумма для вывода средств больше баланса бустера");
            throw new PlatformException(WITHDRAWAL_AMOUNT_MORE_THEN_BALANCE_ERROR);
        }

        log.debug("Уменьшение суммы баланса бустера на сумму, поданную на вывод средств");
        boosterProfile.setBalance(boosterProfile.getBalance().subtract(withdrawalAmount));
        boosterProfileRepository.save(boosterProfile);
    }
}
