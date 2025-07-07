package ru.platform.orders.enumz;

import lombok.Getter;

/**
 * Статусы заказов
 */
@Getter
public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    ON_PENDING,
    COMPLETED
}
