package ru.platform.offers.dto.request;

import lombok.Data;

@Data
public class SelectedOptionToCartDto {

    private String optionId;
    private String optionTitle;
    private Object value;
    private Object label;
}
