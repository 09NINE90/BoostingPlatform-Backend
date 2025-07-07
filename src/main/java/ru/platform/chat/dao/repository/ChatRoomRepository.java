package ru.platform.chat.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.chat.dao.ChatRoomEntity;

import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, UUID> {
}
