package ru.platform.orders.service;

import ru.platform.orders.dao.OrderEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Сервис для работы с заказами в контексте финансовых операций.
 */
public interface IOrderForWorkWithFinanceService {

    /**
     * Помечает все заказы в статусе ожидания как завершённые, если они были завершены до указанной даты.
     */
    void markPendingAsCompleted(OffsetDateTime cutoffDate, OffsetDateTime now);

    /**
     * Получает сущность заказа по его идентификатору.
     */
    OrderEntity getOrderById(UUID orderId);
}

