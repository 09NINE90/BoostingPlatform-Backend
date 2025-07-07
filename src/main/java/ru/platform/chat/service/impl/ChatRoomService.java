package ru.platform.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.chat.dao.ChatMessageEntity;
import ru.platform.chat.dao.ChatRoomEntity;
import ru.platform.chat.dao.repository.ChatMessageRepository;
import ru.platform.chat.dao.repository.ChatRoomRepository;
import ru.platform.chat.dto.ChatMessageDto;
import ru.platform.chat.dto.ChatRoomDto;
import ru.platform.chat.service.IChatRoomService;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements IChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatRoomDto getChatRoomById(UUID roomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new PlatformException(ErrorType.NOT_FOUND_ERROR));

        List<ChatMessageEntity> messages = chatMessageRepository.findAllByChatRoomOrderByCreatedAtAsc(chatRoom);

        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .messages(messages.stream()
                        .map(msg -> ChatMessageDto.builder()
                                .text(msg.getText())
                                .sender(msg.getSender().getProfile().getNickname())
                                .createdAt(msg.getCreatedAt())
                                .chatId(chatRoom.getId().toString())
                                .build())
                        .toList())
                .build();
    }
}
