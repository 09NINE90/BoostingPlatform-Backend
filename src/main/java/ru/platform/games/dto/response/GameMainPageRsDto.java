package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static ru.platform.LocalConstants.Variables.DEFAULT_IMAGE_LINK;
import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMainPageRsDto {

    @Schema(description = "Идентификатор игры", example = DEFAULT_UUID)
    private String id;

    @Schema(description = "Второй id для фрона состояший из названия игры")
    private String secondId;

    @Schema(description = "Название игры", example = "The best game")
    private String name;

    @Schema(description = "Ссылка на изображение игры для sede bar", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;
}
