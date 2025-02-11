package ru.platform.entity.enums;

import lombok.Getter;

@Getter
public enum EOptionTypes {
    SLIDER ("slider"),
    CHECKBOX ("checkbox"),
    SELECTOR ("selector"),
    RADIOBUTTON ("radiobutton");

    private final String type;
    EOptionTypes(String type) {
        this.type = type;
    }
}
