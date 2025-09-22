package ru.platform.orders.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.notification.service.IMailService;
import ru.platform.notification.MailType;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderSessionEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.repository.OrderSessionRepository;
import ru.platform.orders.dto.FinishSessionMailDto;
import ru.platform.orders.dto.StartSessionMailDto;
import ru.platform.orders.dto.request.FinishOrderSessionRqDto;
import ru.platform.orders.dto.request.StartOrderSessionRqDto;
import ru.platform.orders.dto.response.FinishOrderSessionRsDto;
import ru.platform.orders.enumz.SessionStatus;
import ru.platform.orders.service.IOrderSessionService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;
import ru.platform.utils.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderSessionService implements IOrderSessionService {

    private final OrderSessionRepository orderSessionRepository;
    private final OrderRepository orderRepository;
    private final IAuthService authService;
    private final IMailService mailService;

    @Override
    @Transactional
    public void startSession(UUID orderId, StartOrderSessionRqDto request) {
        UserEntity user = authService.getAuthUser();
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PlatformException(ErrorType.NOT_FOUND_ERROR));

        if (hasActiveSession(orderId)) {
            throw new PlatformException(ErrorType.ORDER_SESSION_IS_ALREADY_ACTIVE);
        }

        OrderSessionEntity session = OrderSessionEntity.builder()
                .order(order)
                .user(user)
                .duration(request.getDuration())
                .streamLink(request.getStreamLink())
                .build();

        orderSessionRepository.save(session);

        StartSessionMailDto startSessionMailDto = StartSessionMailDto.builder()
                .gameName(order.getGame().getTitle())
                .serviceName(order.getOfferName())
                .startTime(DateTimeUtils.offsetDateTimeToStringUTC(session.getStartDateTime()))
                .sessionDuration(session.getDuration())
                .streamLink(session.getStreamLink())
                .chatRoomId(order.getChatRoom().getId().toString())
                .orderId(order.getId().toString())
                .build();

        mailService.sendMail(order.getCreator(), MailType.START_SESSION, startSessionMailDto);
    }

    /**
     * Проверка: существуют ли активные сессии по заказу
     *
     * @param orderId id заказа
     * @return true - существуют, false - нет
     */
    private boolean hasActiveSession(UUID orderId) {
        return orderSessionRepository.existsActiveSessionByOrderId(orderId);
    }

    @Override
    @Transactional
    public FinishOrderSessionRsDto completeSession(@NotNull Long orderSessionId, FinishOrderSessionRqDto request) {
        UserEntity user = authService.getAuthUser();

        OrderSessionEntity session = orderSessionRepository.findById(orderSessionId)
                .orElseThrow(() -> new PlatformException(ErrorType.NOT_FOUND_ERROR));

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new PlatformException(ErrorType.ORDER_SESSION_NOT_ACTIVE);
        }

        if (!session.getUser().getId().equals(user.getId())) {
            throw new PlatformException(ErrorType.ACCESS_DENIED_ERROR);
        }

        OffsetDateTime startDate = session.getStartDateTime();
        OffsetDateTime endDate = OffsetDateTime.now();
        String factDuration = getSessionFactDuration(startDate, endDate);

        session.setProgressMessage(request.getProgressMessage());
        session.setImgurLink(request.getImgurLink());
        session.setStatus(SessionStatus.COMPLETED);
        session.setFactDuration(factDuration);
        session.setEndDateTime(endDate);

        OrderSessionEntity updateSession = orderSessionRepository.save(session);

        FinishSessionMailDto finishSessionMailDto = FinishSessionMailDto.builder()
                .gameName(session.getOrder().getGame().getTitle())
                .serviceName(session.getOrder().getOfferName())
                .sessionNotes(updateSession.getProgressMessage())
                .planedSessionDuration(updateSession.getDuration())
                .factSessionDuration(updateSession.getFactDuration())
                .imgurLink(updateSession.getImgurLink())
                .endTime(DateTimeUtils.offsetDateTimeToStringUTC(updateSession.getEndDateTime()))
                .chatRoomId(updateSession.getOrder().getChatRoom().getId().toString())
                .orderId(updateSession.getOrder().getId().toString())
                .build();

        mailService.sendMail(session.getOrder().getCreator(), MailType.FINISH_SESSION, finishSessionMailDto);

        return mapToFinishOrderSession(updateSession);
    }

    /**
     * Получение фактической продолжительности сессии
     *
     * @param startDate Дата и время начала сессии
     * @param endDate   Дата и время окончания сессии
     * @return Фактическое время продолжительности сессии в формате 'X hours Y minutes'
     */
    private String getSessionFactDuration(OffsetDateTime startDate, OffsetDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append(hours == 1 ? " hour " : " hours ");
        }
        if (minutes > 0) {
            sb.append(minutes).append(minutes == 1 ? " minute" : " minutes");
        }

        return sb.toString();
    }

    /**
     * Маппинг ответа на UI
     *
     * @param updateSession Сущность из таблицы с сессиями
     * @return Объект {@link FinishOrderSessionRsDto} для UI
     */
    private FinishOrderSessionRsDto mapToFinishOrderSession(OrderSessionEntity updateSession) {
        return FinishOrderSessionRsDto.builder()
                .duration(updateSession.getDuration())
                .imgurLink(updateSession.getImgurLink())
                .streamLink(updateSession.getStreamLink())
                .factDuration(updateSession.getFactDuration())
                .progressMessage(updateSession.getProgressMessage())
                .build();
    }
}
