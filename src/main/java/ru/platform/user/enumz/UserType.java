package ru.platform.user.enumz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Тип пользователя для реферальной системы")
public enum UserType {
    @Schema(description = "Клиент (заказчик)")
    CLIENT,

    @Schema(description = "Бустер (исполнитель)")
    BOOSTER
}
