package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static ru.platform.LocalConstants.Variables.DEFAULT_IMAGE_LINK;
import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OffersByGameIdRsDto {

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private String id;
    @Schema(description = "Название предложения", example = "Rank boosting")
    private String title;
    @Schema(description = "Описание предложения", example = "Very long description")
    private String description;
    @Schema(description = "Ссылка на картинку предложения", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;
    @Schema(description = "Стоимость предложения", example = "100")
    private BigDecimal price;
}
