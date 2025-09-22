package ru.platform.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Объект для отправки информации о старте сессии клиенту")
public class StartSessionMailDto {

    @Schema(description = "Название игры", requiredMode = REQUIRED, example = "CS 2")
    private String gameName;

    @Schema(description = "Название предложения", requiredMode = REQUIRED, example = "Power Leveling Package")
    private String serviceName;

    @Schema(description = "Время старта сессии", requiredMode = REQUIRED, example = "2025-09-22, 03:42")
    private String startTime;

    @Schema(description = "Продолжительность сессии (в часах)", requiredMode = REQUIRED, example = "3")
    private int sessionDuration;

    @Schema(description = "Ссылка на стрим", requiredMode = NOT_REQUIRED, example = "https://example.com/stream")
    private String streamLink;

    @Schema(description = "UUID чата по заказу", requiredMode = REQUIRED, example = "3")
    private String chatRoomId;

    @Schema(description = "UUID заказа", requiredMode = REQUIRED, example = "3")
    private String orderId;

}
