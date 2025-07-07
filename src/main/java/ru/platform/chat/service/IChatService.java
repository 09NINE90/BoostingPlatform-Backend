package ru.platform.chat.service;

import ru.platform.chat.dto.ChatMessageDto;

import java.util.UUID;

public interface IChatService {
    ChatMessageDto saveMessage(UUID roomId, ChatMessageDto messageDto, String name);
}
