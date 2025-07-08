package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static ru.platform.LocalConstants.Variables.DEFAULT_IMAGE_LINK;

@Data
@Builder
public class CarouselRsDto {

    @Schema(description = "Идентификатор объекта карусели", example = "1")
    private long id;

    @Schema(description = "Заголовок карусели", example = "It is CS2!")
    private String title;

    @Schema(description = "Описание карусели", example = "Sample description")
    private String description;

    @Schema(description = "Ссылка на картинку карусели", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;
}
