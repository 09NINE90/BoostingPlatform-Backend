package ru.platform.orders.service;

import java.time.OffsetDateTime;

public interface IOrderForWorkWithFinanceService {
    void markPendingAsCompleted(OffsetDateTime cutoffDate, OffsetDateTime now);
}
