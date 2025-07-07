package ru.platform.chat.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.chat.dto.ChatMessageDto;
import ru.platform.chat.service.IChatService;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebsocketApi {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatService chatService;

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable UUID roomId, ChatMessageDto messageDto, Principal principal) {

        ChatMessageDto savedMessage = chatService.saveMessage(roomId, messageDto, principal.getName());
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, savedMessage);
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessageDto handleChatMessage(Principal principal, ChatMessageDto message,
                                            SimpMessageHeaderAccessor headerAccessor) {
        if (principal == null) {
            log.error("Principal is null!");
            throw new PlatformException(ErrorType.AUTHORIZATION_ERROR);
        }

        // Устанавливаем отправителя (можно добавить дополнительную логику)
        message.setSender(principal.getName());

        // Обновляем timestamp
        message.setCreatedAt(OffsetDateTime.now());

        log.info("New message from {}: {}", principal.getName(), message.getText());

        return message;
    }

    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    public String ping() {
        return "pong";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String hello(Principal principal, String message) {
        System.out.println("🔍 Principal: " + principal);

        if (principal == null) {
            System.out.println("❌ Principal is null!");
            return "ERRor";
        }
        System.out.println("✅ User: " + principal.getName());
        return "Hello, " + principal.getName() + "! You said: " + message;
    }
}

