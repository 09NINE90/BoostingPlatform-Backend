package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class BoosterOrderHistoryRsDto {

    @Schema(description = "Идентификатор заказа", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Идентификатор заказа для отображения", example = "AAAA-1234")
    private String orderId;

    @Schema(description = "Игра по которой заказ", example = "League of Legends")
    private String gameName;

    @Schema(description = "Описание заказа", example = "Прокачка аккаунта до уровня 30")
    private String orderName;

    @Schema(description = "Дата и время, когда заказ стал выполненным", example = "2025-07-09T15:30:00Z")
    private OffsetDateTime completedAt;

    @Schema(description = "Зарплата бустера за заказ", example = "1500.00")
    private BigDecimal salary;

    @Schema(description = "Статус заказа", example = "CREATED", enumAsRef = true)
    private OrderStatus orderStatus;
}

