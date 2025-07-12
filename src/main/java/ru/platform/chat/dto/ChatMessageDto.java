package ru.platform.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.LocalConstants;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Сообщение в чате")
public class ChatMessageDto {

    @Schema(description = "ID сообщения", example = LocalConstants.Variables.DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Текст сообщения", example = "Hello!")
    private String text;

    @Schema(description = "Отправитель сообщения", example = "zazek")
    private String sender;

    @Schema(description = "Дата и время создания сообщения", example = "2025-07-07 11:49:28.975 +0500")
    private OffsetDateTime createdAt;

    @Schema(description = "Идентификатор чата, к которому привязано сообщение", example = "zazek")
    private String chatId;
}
