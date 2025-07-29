package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.games.enumz.GamePlatform;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformDto {

    @Schema(description = "Идентификатор платформы", example = "1")
    private Long id;

    @Schema(description = "Название платформы", example = "PS", enumAsRef = true)
    private GamePlatform title;

    @Schema(description = "Название платформы для отображения", example = "Play Station")
    private String name;
}
