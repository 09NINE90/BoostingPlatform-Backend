package ru.platform.chat.service;

import ru.platform.chat.dto.ChatRoomDto;

import java.util.UUID;

/**
 * Сервис для работы с чат-комнатами.
 */
public interface IChatRoomService {

    /**
     * Получает информацию о чат-комнате по её идентификатору.
     */
    ChatRoomDto getChatRoomById(UUID roomId);
}
