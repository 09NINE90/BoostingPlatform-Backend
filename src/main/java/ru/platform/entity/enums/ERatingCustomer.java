package ru.platform.entity.enums;

import lombok.Getter;

@Getter
public enum ERatingCustomer {

    LEVEL_1 ("LEVEL_1"),
    LEVEL_2 ("LEVEL_2"),
    LEVEL_3 ("LEVEL_3");

    private final String title;
    ERatingCustomer(String title) {
        this.title = title;
    }

}
