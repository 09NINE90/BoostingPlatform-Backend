package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.offers.enumz.ItemType;

import java.util.List;

@Data
@Builder
public class OfferSectionItemRsDto {

    @Schema(description = "Название секции", example = "What you will get")
    private String title;

    @Schema(description = "Тип секции", example = "ACCORDION", enumAsRef = true)
    private ItemType type;

    @Schema(description = "Описание секции", example = "What you will get")
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
