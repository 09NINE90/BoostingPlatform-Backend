package ru.platform.chat.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.chat.dto.ChatRoomDto;
import ru.platform.chat.service.IChatRoomService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatApi {

    private final IChatRoomService chatRoomService;

    @GetMapping("/room/{roomId}")
    public ChatRoomDto getChatRoom(@PathVariable UUID roomId) {
        return chatRoomService.getChatRoomById(roomId);
    }
}
