package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static ru.platform.LocalConstants.Variables.*;

@Data
@Builder
public class OfferByIdRsDto {

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private String offerId;

    @Schema(description = "Идентификатор игры, привязанной к предложению", example = DEFAULT_UUID)
    private String gameId;

    @Schema(description = "Идентификатор игры, привязанной к предложению", example = "LoE")
    private String secondGameId;

    @Schema(description = "Второй идентификатор предложения", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Название игры", example = "Destiny 2")
    private String gameName;

    @ArraySchema(schema = @Schema(description = "Платформы для игр", example = "PC"))
    private List<String> gamePlatforms;

    @Schema(description = "Название предложения", example = "Rank boosting")
    private String title;

    @Schema(description = "Описание предложения", example = "Very long description")
    private String description;

    @Schema(description = "Ссылка на картинку предложения", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;

    @Schema(description = "Стоимость предложения", example = "100")
    private Double price;

    @Schema(description = "Категории предложения", example = "PVP, PVE")
    private String categories;

    @ArraySchema(schema = @Schema(description = "Список секций для предложения"))
    private List<OfferSectionRsDto> sections;
}
