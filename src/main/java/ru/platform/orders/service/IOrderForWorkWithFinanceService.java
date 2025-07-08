package ru.platform.orders.service;

import ru.platform.orders.dao.OrderEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface IOrderForWorkWithFinanceService {
    void markPendingAsCompleted(OffsetDateTime cutoffDate, OffsetDateTime now);
    OrderEntity getOrderById(UUID orderId);
}
