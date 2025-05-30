package ru.platform.offers.sorting;

import lombok.Getter;

@Getter
public enum OfferSortKeys {

    TITLE("title"),
    PRICE("price"),
    CREATED_AT("createdAt");

    private final String name;

    OfferSortKeys(String name){this.name = name;}
}
