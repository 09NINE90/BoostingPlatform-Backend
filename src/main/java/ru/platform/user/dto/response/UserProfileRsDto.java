package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту данные профиля пользователя")
public class UserProfileRsDto {

    @Schema(description = "ID пользователя для отображения на странице")
    private String secondId;

    @Schema(description = "Имя пользователя")
    private String nickname;

    @Schema(description = "Ссылка на аватарку пользователя")
    private String imageUrl;

}
