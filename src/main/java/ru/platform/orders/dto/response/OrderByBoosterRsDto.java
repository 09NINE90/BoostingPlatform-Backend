package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.platform.games.dto.response.PlatformDto;
import ru.platform.orders.enumz.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@Jacksonized
public class OrderByBoosterRsDto {

    @Schema(description = "Идентификатор чата", example = DEFAULT_UUID)
    private String chatId;

    @Schema(description = "Идентификатор заказа", example = DEFAULT_UUID)
    private String orderId;

    @Schema(description = "Идентификатор заказа для UI", example = "Legend of Eldoria")
    private String secondId;

    @Schema(description = "Название заказа", example = "Legend of Eldoria")
    private String offerName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Название платформы")
    private PlatformDto gamePlatform;

    @Schema(description = "Текущий статус заказа", example = "NEW", enumAsRef = true)
    private OrderStatus orderStatus;

    @Schema(description = "Общая стоимость заказа", example = "150.0")
    private double totalPrice;

    @Schema(description = "ЗП бустера за заказ", example = "150.0")
    private double boosterSalary;

    @Schema(description = "Дата и время взятия заказа в работу", example = "2025-07-07 11:56:09.176 +0500")
    private OffsetDateTime startTimeExecution;

    @Schema(description = "Дата и время завершения выполнения заказа", example = "2025-07-07 11:56:09.176 +0500")
    private OffsetDateTime endTimeExecution;

    @Schema(description = "Дата и время перевода заказа в статус COMPLETED", example = "2025-07-07 11:56:09.176 +0500")
    private OffsetDateTime completedAt;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Выбранные дополнительные опции для заказа",
                    example = """
                [{
                  "optionTitle": "Стрим-сопровождение",
                  "value": "stream_support",
                  "label": "С включением стрима"
                }]"""
            ),
            schema = @Schema(implementation = CartSelectedOptionsDto.class)
    )
    private List<CartSelectedOptionsDto> selectedOptions;

    @Data
    @Builder
    @Jacksonized
    @Schema(description = "Выбранная опция для заказа")
    public static class CartSelectedOptionsDto {

        @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
        private String optionTitle;

        @Schema(description = "Значение, переданное для опции", example = "stream_support")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
        private Object label;
    }
}
