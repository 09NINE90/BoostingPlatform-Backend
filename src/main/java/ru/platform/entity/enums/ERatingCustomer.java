package ru.platform.entity.enums;

public enum ERatingCustomer {

    LEVEL_1 ("LEVEL_1"),
    LEVEL_2 ("LEVEL_2"),
    LEVEL_3 ("LEVEL_3");

    private String title;
    ERatingCustomer(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
