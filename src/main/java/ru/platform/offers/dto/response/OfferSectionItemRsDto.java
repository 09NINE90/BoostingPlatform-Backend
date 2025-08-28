package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.offers.enumz.ItemType;

import java.util.List;

@Data
@Builder
@Schema(description = "Элемент секции предложения")
public class OfferSectionItemRsDto {

    @Schema(description = "Название секции", example = "What you will get")
    private String title;

    @Schema(description = "Тип секции", example = "ACCORDION", enumAsRef = true)
    private ItemType type;

    @Schema(description = "Описание секции", example = "What you will get")
    private String description;

    @Schema(description = "Связанное предложение")
    private RelatedOfferDto relatedOffer;

    @ArraySchema(
            arraySchema = @Schema(description = "Вложенные элементы")
    )
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
