package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.CustomerStatus;

import java.math.BigDecimal;

import static ru.platform.LocalConstants.Variables.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту данные профиля заказчика")
public class CustomerProfileRsDto {

    @Schema(description = "ID пользователя для отображения на странице", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Email пользователя", example = DEFAULT_USER_MAIL)
    private String email;

    @Schema(description = "Имя пользователя", example = DEFAULT_USER_NICKNAME)
    private String nickname;

    @Schema(description = "Ссылка на аватарку пользователя", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;

    @Schema(description = "Процент скидки пользователя", example = "1")
    private Integer discountPercentage;

    @Schema(description = "Статус пользователя", example = "JUNIOR", enumAsRef = true)
    private CustomerStatus status;

    @Schema(description = "Бонусный баланс пользователя", example = "100.00")
    private BigDecimal cashbackBalance;

}
