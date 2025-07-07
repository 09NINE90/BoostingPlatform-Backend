package ru.platform.chat.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.LocalConstants;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ChatRoomDto {

    @Schema(description = "Идентификатор чата", example = LocalConstants.Variables.DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Название чата", example = "Order AAAA-0000")
    private String title;

    @ArraySchema(schema = @Schema(description = "Список сообщений"))
    private List<ChatMessageDto> messages;

}
