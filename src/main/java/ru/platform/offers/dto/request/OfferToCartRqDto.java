package ru.platform.offers.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OfferToCartRqDto {

    private UUID offerId;
    private String gameName;
    private double basePrice;
    private List<SelectedOptionToCartDto> selectedOptions;
    private double totalPrice;
    private int totalTime;

}
