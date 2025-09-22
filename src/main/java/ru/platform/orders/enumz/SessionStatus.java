package ru.platform.orders.enumz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Статусы сессий
 */
@Getter
@Schema(description = "Статусы сессий")
public enum SessionStatus {
    ACTIVE,
    COMPLETED
}
