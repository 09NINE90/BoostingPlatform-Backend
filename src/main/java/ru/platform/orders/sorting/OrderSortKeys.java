package ru.platform.orders.sorting;

import lombok.Getter;

@Getter
public enum OrderSortKeys {
    OFFER_NAME("offerName"),
    TOTAL_PRICE("totalPrice"),
    BOOSTER_PRICE("boosterSalary"),
    GAME_NAME("game"),
    GAME_PLATFORM("gamePlatform"),
    CREATION_AT("createdAt"),
    START_TIME_EXECUTION("startTimeExecution");

    private final String title;

    OrderSortKeys(String title){this.title = title;}
}
