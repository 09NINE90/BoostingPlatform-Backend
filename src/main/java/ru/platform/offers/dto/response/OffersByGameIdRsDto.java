package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OffersByGameIdRsDto {

    @Schema(description = "Идентификатор предложения")
    private String offerId;
    @Schema(description = "Название предложения")
    private String offerTitle;
    @Schema(description = "Описание предложения")
    private String offerDescription;
    @Schema(description = "Ссылка на картинку предложения")
    private String offerImageUrl;
    @Schema(description = "Стоимость предложения", example = "100")
    private Float offerPrice;
}
