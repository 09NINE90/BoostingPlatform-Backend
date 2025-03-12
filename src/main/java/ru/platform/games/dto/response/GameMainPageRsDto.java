package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMainPageRsDto {

    @Schema(description = "Идентификатор игры")
    private String gameId;
    @Schema(description = "Название игры")
    private String gameTitle;

}
