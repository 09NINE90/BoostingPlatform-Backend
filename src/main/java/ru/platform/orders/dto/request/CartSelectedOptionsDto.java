package ru.platform.orders.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartSelectedOptionsDto {

    private String optionTitle;
    private Object value;
    private Object label;
}
