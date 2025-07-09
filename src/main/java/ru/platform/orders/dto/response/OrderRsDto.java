package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.platform.orders.enumz.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
@Jacksonized
public class OrderRsDto {

    @Schema(description = "Идентификатор чата", example = "UUID")
    private String chatId;

    @Schema(description = "Идентификатор бустера, прикрепленного к заказу", example = DEFAULT_UUID)
    private String boosterId;

    @Schema(description = "Идентификатор заказа", example = "Legend of Eldoria")
    private String orderId;

    @Schema(description = "Идентификатор заказа для UI", example = "Legend of Eldoria")
    private String secondId;

    @Schema(description = "Название заказа", example = "Legend of Eldoria")
    private String offerName;

    @Schema(description = "Название игры", example = "Legend of Eldoria")
    private String gameName;

    @Schema(description = "Название платформы", example = "XBOX")
    private String gamePlatform;

    @Schema(description = "Текущий статус заказа", example = "NEW", enumAsRef = true)
    private OrderStatus orderStatus;

    @Schema(description = "Общая стоимость заказа", example = "150.0")
    private BigDecimal totalPrice;

    @Schema(description = "Дата и время (по UTC) взятия заказа в работу", example = "2025-07-07 11:49:57.595 +0500")
    private OffsetDateTime startTimeExecution;

    @Schema(description = "Дата и время (по UTC) завершения выполнения заказа", example = "2025-07-07 11:49:57.595 +0500")
    private OffsetDateTime endTimeExecution;

    @ArraySchema(schema = @Schema(description = "Список опций, выбранных для заказа"))
    private List<CartSelectedOptionsDto> selectedOptions;

    @Data
    @Builder
    @Jacksonized
    public static class CartSelectedOptionsDto {

        @Schema(description = "Название опции, выбранной пользователем", example = "Стрим-сопровождение")
        private String optionTitle;

        @Schema(description = "Значение, переданное для опции", example = "stream_support")
        private Object value;

        @Schema(description = "Метка, отображаемая пользователю", example = "С включением стрима")
        private Object label;
    }
}
