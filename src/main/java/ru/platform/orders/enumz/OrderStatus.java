package ru.platform.orders.enumz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Статусы заказов
 */
@Getter
@Schema(description = "Статусы заказов")
public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    ON_PENDING,
    COMPLETED
}
