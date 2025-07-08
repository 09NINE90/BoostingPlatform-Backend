package ru.platform.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.service.IOrderForWorkWithFinanceService;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.platform.orders.enumz.OrderStatus.COMPLETED;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderForWorkWithFinanceService implements IOrderForWorkWithFinanceService {

    private final OrderRepository orderRepository;

    /**
     * Метод для изменения статусов заказов на COMPLETED,
     * если прошло время проверки заказа
     */
    @Override
    @Transactional
    public void markPendingAsCompleted(OffsetDateTime cutoffDate, OffsetDateTime now) {
        log.debug("Начало процесса изменения статусов заказов");

        int updatedOrders = orderRepository.markPendingAsCompleted(cutoffDate, COMPLETED, now);

        if (updatedOrders == 0) {
            log.debug("Нет заказов для перевода в COMPLETED");
        } else {
            log.debug("В статус COMPLETED переведено {} заказов", updatedOrders);
        }
    }

    @Override
    public OrderEntity getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new PlatformException(ErrorType.NOT_FOUND_ERROR)
        );
    }
}
