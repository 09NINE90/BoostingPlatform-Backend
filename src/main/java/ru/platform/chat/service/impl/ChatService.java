package ru.platform.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.chat.dao.ChatMessageEntity;
import ru.platform.chat.dao.ChatRoomEntity;
import ru.platform.chat.dao.repository.ChatMessageRepository;
import ru.platform.chat.dao.repository.ChatRoomRepository;
import ru.platform.chat.dto.ChatMessageDto;
import ru.platform.chat.service.IChatService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessageDto saveMessage(UUID roomId, ChatMessageDto messageDto, String senderEmail) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        UserEntity sender = userRepository.findByUsername(senderEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessageEntity saved = chatMessageRepository.save(ChatMessageEntity.builder()
                .chatRoom(room)
                .sender(sender)
                .text(messageDto.getText())
                .createdAt(OffsetDateTime.now())
                .build());

        return ChatMessageDto.builder()
                .id(saved.getId())
                .chatId(roomId.toString())
                .text(saved.getText())
                .sender(sender.getProfile().getNickname())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
