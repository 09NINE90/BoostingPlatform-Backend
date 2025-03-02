package ru.platform.offers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OffersByGameIdRsDto {
    private String offerId;
    private String offerTitle;
    private String offerDescription;
    private String offerImageUrl;
    private Float offerPrice;
}
