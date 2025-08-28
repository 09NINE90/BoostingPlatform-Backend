package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "Категория игры")
public class CategoryRsDto {

    @Schema(description = "Идентификатор категории", example = "3")
    private long id;

    @Schema(description = "Название категории", example = "PVP")
    private String name;

}
