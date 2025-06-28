package ru.platform.finance.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.PlatformException;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.dao.repository.BoosterFinancialRecordRepository;
import ru.platform.finance.dto.HandleWithdrawalRqDto;
import ru.platform.finance.service.IBoosterFinanceService;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IBoosterService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static ru.platform.exception.ErrorType.*;
import static ru.platform.finance.enumz.PaymentStatus.ON_PENDING;
import static ru.platform.finance.enumz.RecordType.SALARY;
import static ru.platform.finance.enumz.RecordType.WITHDRAWAL;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoosterFinanceService implements IBoosterFinanceService {

    private final IAuthService authService;
    private final IBoosterService boosterService;
    private final BoosterFinancialRecordRepository boosterFinancialRecordRepository;

    private final String LOG_PREFIX = "Сервис обработки балансов бустера: {}";

    /**
     * Создание записи баланса с ЗП бустера
     */
    @Override
    @Transactional
    public void createNewRecordOfSalaryBooster(OrderEntity order) {
        if (order.getBooster() == null || order.getBoosterSalary() == null) {
            log.error(LOG_PREFIX, "Переданные данные о бустере или его зарплате равны NULL");
            throw new PlatformException(MISSING_REQUIRED_FIELDS_ERROR);
        }
        log.debug(LOG_PREFIX, "Сохранение новой записи баланса о зарплате бустера");
        boosterFinancialRecordRepository.save(BoosterFinancialRecordEntity.builder()
                .order(order)
                .booster(order.getBooster())
                .recordType(SALARY)
                .status(ON_PENDING)
                .amount(order.getBoosterSalary())
                .createdAt(OffsetDateTime.now())
                .calculated(false)
                .build());
    }

    /**
     * Обработка запроса на вывод средств бустера
     */
    @Override
    @Transactional
    public void handleWithdrawal(HandleWithdrawalRqDto request) {
        log.debug(LOG_PREFIX, "Начало обработки запроса на вывод средств");
        UserEntity booster = authService.getAuthUser();

        log.debug(LOG_PREFIX, "Валидация суммы из запроса на вывод средств");
        BigDecimal withdrawalAmount = request.getWithdrawalAmount();
        boosterService.checkBoosterBalance(booster, withdrawalAmount);

        log.debug(LOG_PREFIX, "Создание записи о запросе на вывод средств");
        boosterFinancialRecordRepository.save(BoosterFinancialRecordEntity.builder()
                .amount(request.getWithdrawalAmount())
                .createdAt(OffsetDateTime.now())
                .recordType(WITHDRAWAL)
                .status(ON_PENDING)
                .booster(booster)
                .build());
    }

}
