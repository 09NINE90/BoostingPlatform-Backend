package ru.platform.entity.enums;

public enum ERoles {
    ADMIN ("ADMIN"),
    CUSTOMER ("CUSTOMER"),
    BOOSTER ("BOOSTER"),
    USER ("USER"),
    MANAGER ("MANAGER");

    private String title;
    ERoles(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
