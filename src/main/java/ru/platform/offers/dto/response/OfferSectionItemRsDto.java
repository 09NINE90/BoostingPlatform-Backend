package ru.platform.offers.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.offers.enumz.ItemType;

import java.util.List;

@Data
@Builder
public class OfferSectionItemRsDto {

    private String title;
    private ItemType type;
    private String description;
    private RelatedOfferDto relatedOffer;
    private List<OfferSectionItemRsDto> items;


    @Data
    @Builder
    public static class RelatedOfferDto {
        private String offerId;
        private String imageUrl;
        private Double price;
        private String title;
    }

}
