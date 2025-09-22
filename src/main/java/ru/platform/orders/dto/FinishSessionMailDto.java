package ru.platform.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Объект для отправки информации об окончании сессии клиенту")
public class FinishSessionMailDto {

    @Schema(description = "Название игры", requiredMode = REQUIRED, example = "CS 2")
    private String gameName;

    @Schema(description = "Название предложения", requiredMode = REQUIRED, example = "Power Leveling Package")
    private String serviceName;

    @Schema(description = "Описание выполненной работы", requiredMode = REQUIRED, example = "What i do")
    private String sessionNotes;

    @Schema(description = "Заявленная продолжительность сессии (в часах)", requiredMode = REQUIRED, example = "3")
    private int planedSessionDuration;

    @Schema(description = "Фактическая продолжительность сессии", requiredMode = REQUIRED, example = "3 hours 2 minutes")
    private String factSessionDuration;

    @Schema(description = "Время окончания сессии", requiredMode = REQUIRED, example = "2025-09-22, 03:42")
    private String endTime;

    @Schema(description = "Ссылка на imgur для отслеживания прогресса", requiredMode = REQUIRED, example = "https://example.com/imgur")
    private String imgurLink;

    @Schema(description = "UUID чата по заказу", requiredMode = REQUIRED, example = "3")
    private String chatRoomId;

    @Schema(description = "UUID заказа", requiredMode = REQUIRED, example = "3")
    private String orderId;
}
