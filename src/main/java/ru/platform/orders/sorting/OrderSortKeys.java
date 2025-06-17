package ru.platform.orders.sorting;

import lombok.Getter;

@Getter
public enum OrderSortKeys {
    OFFER_NAME("offerName"),
    PRICE("totalPrice"),
    GAME_NAME("gameName"),
    GAME_PLATFORM("gamePlatform"),
    CREATION_AT("createdAt");

    private final String title;

    OrderSortKeys(String title){this.title = title;}
}
