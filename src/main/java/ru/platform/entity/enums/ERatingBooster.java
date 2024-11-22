package ru.platform.entity.enums;

public enum ERatingBooster {

    LEVEL_1 ("Junior"),
    LEVEL_2 ("Middle"),
    LEVEL_3 ("Senior");

    private String title;
    ERatingBooster(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
