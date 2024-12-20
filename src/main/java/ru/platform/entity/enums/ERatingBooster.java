package ru.platform.entity.enums;

import lombok.Getter;

@Getter
public enum ERatingBooster {

    LEVEL_1 ("Junior"),
    LEVEL_2 ("Middle"),
    LEVEL_3 ("Senior");

    private final String title;
    ERatingBooster(String title) {
        this.title = title;
    }

}
