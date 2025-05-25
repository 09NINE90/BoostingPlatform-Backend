package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
public class CategoryRsDto {

    @Schema(description = "Идентификатор категории", example = DEFAULT_UUID)
    private String id;

    @Schema(description = "Название категории", example = "PVP")
    private String name;

}
