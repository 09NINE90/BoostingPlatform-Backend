package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@Jacksonized
public class CategoryRsDto {

    @Schema(description = "Идентификатор категории", example = "3")
    private long id;

    @Schema(description = "Название категории", example = "PVP")
    private String name;

}
