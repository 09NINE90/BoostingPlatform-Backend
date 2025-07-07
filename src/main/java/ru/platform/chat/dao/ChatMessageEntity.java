package ru.platform.chat.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.dao.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String text;
    private OffsetDateTime createdAt;

    @ManyToOne
    private ChatRoomEntity chatRoom;

    @ManyToOne
    private UserEntity sender;
}
