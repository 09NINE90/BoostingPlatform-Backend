package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Schema(description = "Уровень бустера", example = "1")
    private int level;

    @Schema(description = "Процент с заказа бустера", example = "")
    private Double percentageOfOrder;

    @Schema(description = "Баланс бустера", example = "100.00")
    private BigDecimal balance;

    @Schema(description = "Суммарный заработок бустера", example = "999.99")
    private BigDecimal totalIncome;

    @Schema(description = "Сумма чаевых бустера", example = "666.66")
    private BigDecimal totalTips;
}
