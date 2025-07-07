package ru.platform.chat.service;

import ru.platform.chat.dto.ChatRoomDto;

import java.util.UUID;

public interface IChatRoomService {
    ChatRoomDto getChatRoomById(UUID roomId);
}
