package ru.platform.chat.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.chat.dao.ChatMessageEntity;
import ru.platform.chat.dao.ChatRoomEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findAllByChatRoomOrderByCreatedAtAsc(ChatRoomEntity chatRoom);
}
