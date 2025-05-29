package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.platform.LocalConstants.Variables.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту данные профиля пользователя")
public class UserProfileRsDto {

    @Schema(description = "ID пользователя для отображения на странице", example = DEFAULT_SECOND_UUID)
    private String secondId;

    @Schema(description = "Имя пользователя", example = DEFAULT_USER_NICKNAME)
    private String nickname;

    @Schema(description = "Ссылка на аватарку пользователя", example = DEFAULT_IMAGE_LINK)
    private String imageUrl;

}
