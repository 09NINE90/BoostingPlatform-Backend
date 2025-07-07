package ru.platform.finance.enumz;

import lombok.Getter;

/**
 * Статусы операций в таблице записей балансов бустера
 * ON_PENDING - в процессе
 * COMPLETED - выполнено
 */
@Getter
public enum PaymentStatus {
    ON_PENDING,
    COMPLETED
}
