package ru.platform.orders.enumz;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    ON_CHECKING,
    COMPLETED
}
