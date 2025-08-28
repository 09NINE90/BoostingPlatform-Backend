package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.games.dto.response.PlatformDto;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.*;

@Data
@Builder
@Schema(description = "Детальная информация по предложению")
public class OfferDetailsRsDto {

    @Schema(description = "Идентификатор предложения", example = DEFAULT_UUID)
    private UUID offerId;

    @Schema(description = "Идентификатор игры, привязанной к предложению", example = DEFAULT_UUID)
    private UUID gameId;

    @Schema(description = "Идентификатор игры, привязанной к предложению", example = "LoE")
    private String secondGameId;

    @Schema(description = "Второй идентификатор предложения", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Название игры", example = "Destiny 2")
    private String gameName;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Доступные игровые платформы",
                    example = "[\"PC\", \"PS\"]"
            ),
            schema = @Schema(
                    description = "Платформа",
                    example = "PC",
                    minLength = 1,
                    maxLength = 10
            )
    )
    private List<PlatformDto> gamePlatforms;

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

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Секции с дополнительной информацией о предложении",
                    example = """
                [{
                  "title": "Что вы получите",
                  "type": "ACCORDION",
                  "description": "Описание преимуществ",
                  "items": []
                }]"""
            ),
            schema = @Schema(implementation = OfferSectionRsDto.class)
    )
    private List<OfferSectionRsDto> sections;
}
