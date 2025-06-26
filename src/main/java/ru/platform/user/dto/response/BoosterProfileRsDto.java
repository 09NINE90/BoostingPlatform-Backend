package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.BoosterLevelName;

import java.math.BigDecimal;
import java.util.List;

import static ru.platform.LocalConstants.Variables.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту данные профиля бустера")
public class BoosterProfileRsDto {

    @Schema(description = "ID пользователя для отображения на странице", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Email пользователя", example = DEFAULT_USER_MAIL)
    private String email;

    @Schema(description = "Имя пользователя", example = DEFAULT_USER_NICKNAME)
    private String nickname;

    @Schema(description = "Ссылка на аватарку пользователя", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;

    @Schema(description = "Статус пользователя (описание профиля)")
    private String description;

    @Schema(description = "Уровень бустера", example = "ROOKIE")
    private BoosterLevelName level;

    @Schema(description = "Следующий уровень бустера", example = "VETERAN")
    private BoosterLevelName nextLevel;

    @Schema(description = "Количество выполненных бустером заказов", example = "10")
    private long numberOfCompletedOrders;

    @Schema(description = "Процент с заказа бустера", example = "50")
    private Double percentageOfOrder;

    @Schema(description = "Процент прогресса аккаунта бустера", example = "50")
    private BigDecimal progressAccountStatus;

    @Schema(description = "Баланс денег бустера, доступных для вывода", example = "100.00")
    private BigDecimal balance;

    @Schema(description = "Суммарный заработок бустера", example = "999.99")
    private BigDecimal totalIncome;

    @Schema(description = "Сумма чаевых бустера", example = "666.66")
    private BigDecimal totalTips;

    @ArraySchema(schema = @Schema(description = "Список игровых тегов бустера"))
    private List<GameTag> gameTags;

    @Data
    @Builder
    public static class GameTag {

        @Schema(description = "Идентификатор тега", example = DEFAULT_UUID)
        private String id;

        @Schema(description = "Название игры", example = "Legend of Eldoria")
        private String name;
    }
}
