package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMainPageRsDto {

    @Schema(description = "Идентификатор игры", example = DEFAULT_UUID)
    private String gameId;
    @Schema(description = "Название игры", example = "The best game")
    private String gameTitle;

}
