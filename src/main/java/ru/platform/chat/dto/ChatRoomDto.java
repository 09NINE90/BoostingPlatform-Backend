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
@Schema(description = "Представление чат-комнаты")
public class ChatRoomDto {

    @Schema(description = "Идентификатор чата", example = LocalConstants.Variables.DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Название чата", example = "Order AAAA-0000")
    private String title;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Список сообщений в чате",
                    example = """
                                [
                              {
                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                "text": "Привет! Как продвигается заказ?",
                                "sender": "user123",
                                "createdAt": "2025-07-07T11:49:28.975+05:00",
                                "chatId": "550e8400-e29b-41d4-a716-446655440001"
                              }
                            ]"""
            ),
            schema = @Schema(implementation = ChatMessageDto.class)
    )
    private List<ChatMessageDto> messages;

}
