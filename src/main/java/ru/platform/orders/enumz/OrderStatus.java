package ru.platform.orders.enumz;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    CLOSED
}
