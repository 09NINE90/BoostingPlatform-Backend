package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
public class GameItemRsDto {

    @Schema(description = "Идентификатор игры", example = DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Название игры", example = "The best game")
    private String name;
}
