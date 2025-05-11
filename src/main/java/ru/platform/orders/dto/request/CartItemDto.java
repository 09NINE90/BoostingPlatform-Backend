package ru.platform.orders.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartItemDto {

    private UUID offerId;
    private String gameName;
    private double basePrice;
    private List<CartSelectedOptionsDto> selectedOptions;
    private double totalPrice;
    private int totalTime;
}
