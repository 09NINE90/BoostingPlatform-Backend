package ru.platform.finance.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.dao.repository.BoosterFinancialRecordRepository;
import ru.platform.finance.enumz.PaymentStatus;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.orders.service.IOrderForWorkWithFinanceService;
import ru.platform.user.service.IBoosterService;
import ru.platform.utils.DateTimeUtils;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.platform.LocalConstants.DateTimeConstants.ONE_MINUTE;
import static ru.platform.LocalConstants.DateTimeConstants.TEN_MINUTES;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoosterFinanceScheduleService {

    private final IBoosterService boosterService;
    private final IOrderForWorkWithFinanceService orderForWorkWithFinanceService;
    private final BoosterFinancialRecordRepository boosterFinancialRecordRepository;

    // Настройки schedule
    private final int FIXED_RATE_SCHEDULING = TEN_MINUTES;
    private final int INITIAL_DELAY_SCHEDULING = ONE_MINUTE;

    @Transactional
    @PlatformMonitoring(name = MonitoringMethodType.BOOSTER_FINANCE_PROCESS_RECORDS)
    @Scheduled(initialDelay = INITIAL_DELAY_SCHEDULING, fixedRate = FIXED_RATE_SCHEDULING)
    public void processFinancialRecords() {
        log.debug("Начало процесса пересчета балансов бустеров");
        OffsetDateTime cutoffTime = DateTimeUtils.getRecordOlderThan1Minute();
        OffsetDateTime now = OffsetDateTime.now();

        log.debug("Cutoff time (UTC) для обработки записей: {}", DateTimeUtils.offsetDateTimeToStringUTC(cutoffTime));
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
