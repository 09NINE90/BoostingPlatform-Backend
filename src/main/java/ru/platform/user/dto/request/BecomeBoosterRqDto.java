package ru.platform.user.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Заявка на присоединение к бустерам")
public class BecomeBoosterRqDto {

    @NotNull
    @NotBlank
    @Schema(description = "Игровой никнейм", example = "BoostMaster123")
    private String nickname;

    @NotNull
    @NotBlank
    @Schema(description = "Email для связи", example = "boost@example.com")
    private String email;

    @Schema(description = "Discord-тег", example = "BoostMaster#1234")
    private String discordTag;

    @NotNull
    @NotEmpty
    @ArraySchema(
            arraySchema = @Schema(
                    description = "Список игр, в которых пользователь хочет бустить",
                    example = "[\"Diablo 4\", \"World of Warcraft\"]"
            ),
            schema = @Schema(
                    description = "Название игры",
                    example = "Diablo 4"
            )
    )
    private List<String> selectedGames;

    @Schema(description = "Другие игры (если нет в списке)", example = "Path of Exile")
    private String customGames;

    @NotNull
    @NotBlank
    @Schema(description = "Опыт в играх", example = "5 лет в MMO")
    private String gamingExperience;

    @NotNull
    @NotBlank
    @Schema(description = "Опыт бустинга", example = "Помогал в топ-10 гильдии WoW")
    private String boostingExperience;

    @Schema(description = "Ссылки на трекеры (например, Raider.IO)", example = "https://raider.io/characters/eu/BoostMaster")
    private String trackerLinks;

    @Schema(description = "Ссылки на скриншоты прогресса", example = "https://imgur.com/a/boost123")
    private String progressImages;

    @Schema(description = "Дополнительная информация", example = "Готов работать ночью")
    private String additionalInfo;
}
