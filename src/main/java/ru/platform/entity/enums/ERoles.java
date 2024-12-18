package ru.platform.entity.enums;

import lombok.Getter;

@Getter
public enum ERoles {
    ADMIN ("ADMIN"),
    CUSTOMER ("CUSTOMER"),
    BOOSTER ("BOOSTER"),
    USER ("USER"),
    MANAGER ("MANAGER");

    private final String title;
    ERoles(String title) {
        this.title = title;
    }

}
