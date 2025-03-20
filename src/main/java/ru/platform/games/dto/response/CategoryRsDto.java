package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRsDto {

    @Schema(description = "Название категории", example = "PVP")
    private String name;

}
