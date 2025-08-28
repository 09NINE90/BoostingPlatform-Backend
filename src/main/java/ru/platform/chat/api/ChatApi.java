package ru.platform.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.chat.dto.ChatRoomDto;
import ru.platform.chat.service.IChatRoomService;

import java.util.UUID;

import static ru.platform.LocalConstants.Api.CHAT_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.CHAT_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = CHAT_TAG_NAME, description = CHAT_TAG_DESCRIPTION)
public class ChatApi {

    private final IChatRoomService chatRoomService;

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Получение чата по идентификатору")
    public ChatRoomDto getChatRoom(@PathVariable UUID roomId) {
        return chatRoomService.getChatRoomById(roomId);
    }
}
