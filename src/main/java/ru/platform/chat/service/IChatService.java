package ru.platform.chat.service;

import ru.platform.chat.dto.ChatMessageDto;

import java.util.UUID;

/**
 * Сервис для работы с сообщениями в чатах.
 */
public interface IChatService {

    /**
     * Сохраняет сообщение в указанной чат-комнате.
     */
    ChatMessageDto saveMessage(UUID roomId, ChatMessageDto messageDto, String name);
}
