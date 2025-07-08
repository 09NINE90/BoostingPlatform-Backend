package ru.platform.finance.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.PlatformException;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.dao.repository.BoosterFinancialRecordRepository;
import ru.platform.finance.dto.request.HandleSendTipRqDto;
import ru.platform.finance.dto.request.HandleWithdrawalRqDto;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.finance.dto.response.OrderTipHistoryRsDto;
import ru.platform.finance.mapper.BalanceMapper;
import ru.platform.finance.service.IBoosterFinanceService;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.service.IOrderForWorkWithFinanceService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IBoosterService;
import ru.platform.utils.DateTimeUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static ru.platform.exception.ErrorType.*;
import static ru.platform.finance.enumz.PaymentStatus.ON_PENDING;
import static ru.platform.finance.enumz.RecordType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoosterFinanceService implements IBoosterFinanceService {

    private final IAuthService authService;
    private final BalanceMapper balanceMapper;
    private final IBoosterService boosterService;
    private final IOrderForWorkWithFinanceService orderForWorkWithFinanceService;
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

    /**
     * Получение данных об истории баланса бустера
     */
    @Override
    public List<BalanceHistoryRsDto> getBalanceHistoryByBooster() {
        log.debug(LOG_PREFIX, "Начало обработки запроса на получение истории баланса бустера");
        UserEntity booster = authService.getAuthUser();

        log.debug(LOG_PREFIX, "Получение записей о балансах бустера из БД");
        List<BoosterFinancialRecordEntity> balanceList = boosterFinancialRecordRepository.findAllByBooster(booster);

        return balanceList.stream()
                .map(balanceMapper::toBalanceHistoryRsDto)
                .toList();
    }

    /**
     * Оставление чаевых за заказ
     */
    @Override
    @Transactional
    public void postHandleSendTip(HandleSendTipRqDto request) {
        OrderEntity order = orderForWorkWithFinanceService.getOrderById(request.getOrderId());
        BoosterFinancialRecordEntity newRecord = BoosterFinancialRecordEntity.builder()
                .createdAt(OffsetDateTime.now())
                .amount(request.getTipAmount())
                .booster(order.getBooster())
                .status(ON_PENDING)
                .recordType(TIP)
                .order(order)
                .build();

        boosterFinancialRecordRepository.save(newRecord);
    }

    /**
     * Получение истории чаевых к заказу по ID заказа
     */
    @Override
    public List<OrderTipHistoryRsDto> getOrderTipHistory(UUID orderId) {
        List<BoosterFinancialRecordEntity> balanceList = boosterFinancialRecordRepository.findAllTipByOrderId(orderId);

        if (balanceList.isEmpty()) return null;

        return balanceList.stream()
                .map(this::mapToTipHistory)
                .toList();
    }

    private OrderTipHistoryRsDto mapToTipHistory(BoosterFinancialRecordEntity entity) {
        return OrderTipHistoryRsDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(DateTimeUtils.offsetDateTimeToStringUTC(entity.getCreatedAt()))
                .build();
    }

}
