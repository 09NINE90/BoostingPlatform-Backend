package ru.platform.orders.service;

import jakarta.validation.constraints.NotNull;
import ru.platform.orders.dto.request.FinishOrderSessionRqDto;
import ru.platform.orders.dto.request.StartOrderSessionRqDto;
import ru.platform.orders.dto.response.FinishOrderSessionRsDto;

import java.util.UUID;

/**
 * Сервис для работы с сессиями по заказам
 */
public interface IOrderSessionService {

    /**
     * Создать сессию по заказу
     *
     * @param orderId id заказа
     * @param request Объект {@link StartOrderSessionRqDto} запроса для создания сессии
     */
    void startSession(UUID orderId, StartOrderSessionRqDto request);

    /**
     * Завершить сессию по заказу
     *
     * @param orderSessionId id заказа
     * @param request        Тело запроса для завершения сессии
     * @return Объект {@link FinishOrderSessionRsDto} для UI
     */
    FinishOrderSessionRsDto completeSession(@NotNull Long orderSessionId, FinishOrderSessionRqDto request);
}
