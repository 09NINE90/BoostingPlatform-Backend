package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.user.enumz.BoosterLevelName;

import static ru.platform.LocalConstants.Variables.DEFAULT_IMAGE_LINK;
import static ru.platform.LocalConstants.Variables.DEFAULT_USER_NICKNAME;

@Data
@Builder
public class MiniBoosterProfileRsDto {

    @Schema(description = "Ссылка на аватарку бустера", example = DEFAULT_IMAGE_LINK)
    private String avatarUrl;

    @Schema(description = "Никнейм бустера", example = DEFAULT_USER_NICKNAME)
    private String boosterName;

    @Schema(description = "Описание профиля бустера", example = "Very long description")
    private String boosterDescription;

    @Schema(description = "Количество выполненных заказов бустера", example = "10")
    private int numberOfCompletedOrders;

    @Schema(description = "Уровень бустера", example = "ROOKIE")
    private BoosterLevelName boosterLevel;
}
