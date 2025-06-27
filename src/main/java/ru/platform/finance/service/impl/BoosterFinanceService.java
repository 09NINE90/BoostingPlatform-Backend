package ru.platform.finance.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.PlatformException;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.dao.repository.BoosterFinancialRecordRepository;
import ru.platform.finance.enumz.PaymentStatus;
import ru.platform.finance.service.IBoosterFinanceService;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.service.IOrderForWorkWithFinanceService;
import ru.platform.user.service.IBoosterService;
import ru.platform.utils.DateTimeUtils;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.platform.LocalConstants.DateTimeConstants.TEN_MINUTES;
import static ru.platform.exception.ErrorType.MISSING_REQUIRED_FIELDS_ERROR;
import static ru.platform.finance.enumz.PaymentStatus.ON_PENDING;
import static ru.platform.finance.enumz.RecordType.SALARY;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoosterFinanceService implements IBoosterFinanceService {

    private final IBoosterService boosterService;
    private final IOrderForWorkWithFinanceService orderForWorkWithFinanceService;
    private final BoosterFinancialRecordRepository boosterFinancialRecordRepository;

    private final String LOG_PREFIX = "BoosterFinanceService: {}";
    private final int TIME_FOR_SCHEDULING_FINANCIAL_RECORDS = TEN_MINUTES;

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

    @Transactional
    @Scheduled(initialDelay = 5000, fixedRate = TIME_FOR_SCHEDULING_FINANCIAL_RECORDS)
    public void processFinancialRecords() {
        log.debug("Начало процесса пересчета балансов бустеров");
        OffsetDateTime cutoffTime = DateTimeUtils.getRecordOlderThan5Minutes();
        OffsetDateTime now = OffsetDateTime.now();
        log.debug("Cutoff time для обработки записей: {}", cutoffTime);
        int updatedRecords = boosterFinancialRecordRepository.markPendingAsCompleted(
                cutoffTime,
                PaymentStatus.COMPLETED,
                now
        );

        log.debug("Запрос на изменение статусов заказов");
        orderForWorkWithFinanceService.markPendingAsCompleted(cutoffTime, now);

        if (updatedRecords > 0) {
            log.debug("В статус COMPLETED переведено {} финансовых записей", updatedRecords);

            log.debug("Поиск записей для изменения баланса бустера");
            List<BoosterFinancialRecordEntity> records = boosterFinancialRecordRepository.findUncalculatedCompleted();
            log.debug("Найдено {} записей для обработки", records.size());

            records.forEach(this::processRecord);
        } else {
            log.debug("Нет записей для перевода в COMPLETED");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processRecord(BoosterFinancialRecordEntity record) {
        try {
            boosterFinancialRecordRepository.findByIdForUpdate(record.getId())
                    .ifPresent(lockedRecord -> {
                        boosterService.updateBalance(
                                lockedRecord.getBooster().getBoosterProfile().getId(),
                                lockedRecord.getAmount(),
                                lockedRecord.getRecordType()
                        );

                        lockedRecord.setCalculated(true);
                        boosterFinancialRecordRepository.save(lockedRecord);
                    });
        } catch (Exception e) {
            log.error("Ошибка обработки записи {}: {}", record.getId(), e.getMessage());
        }
    }
}
