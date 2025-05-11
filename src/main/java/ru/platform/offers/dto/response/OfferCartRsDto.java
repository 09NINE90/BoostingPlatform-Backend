package ru.platform.offers.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.offers.dto.request.SelectedOptionToCartDto;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OfferCartRsDto {

    private UUID offerId;
    private String gameName;
    private List<SelectedOptionToCartDto> selectedOptions;
    private double totalPrice;
    private int totalTime;
}
