package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_SECOND_UUID;
import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@Jacksonized
@Schema(description = "Получение игры по второму идентификатору")
public class GameBySecondIdRsDto {

    @Schema(description = "Идентификатор игры", example = DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Второй идентификатор игры", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Название игры", example = "Destiny 2")
    private String name;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Список категорий игры",
                    example = """
                [
                  {
                    "id": 3,
                    "name": "PVP"
                  },
                  {
                    "id": 5,
                    "name": "Шутер"
                  }
                ]"""
            ),
            schema = @Schema(
                    description = "Категория игры",
                    implementation = CategoryRsDto.class
            )
    )
    private List<CategoryRsDto> categories;
}

