package ru.platform.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ESortKeys {

    @JsonProperty("PRICE")
    PRICE("basePrice"),
    @JsonProperty("CREATED_AT")
    CREATED_AT("createdAt");

    private final String name;
    ESortKeys(String name){this.name = name;}
}
