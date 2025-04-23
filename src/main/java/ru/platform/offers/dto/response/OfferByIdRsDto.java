package ru.platform.offers.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OfferByIdRsDto {

    private String offerId;
    private String gameId;
    private String secondId;
    private String gameName;
    private String title;
    private String description;
    private String imageUrl;
    private Double price;
    private String categories;
    private List<OfferSectionRsDto> sections;
}
