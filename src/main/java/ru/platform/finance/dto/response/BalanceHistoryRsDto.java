package ru.platform.finance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.finance.enumz.PaymentStatus;
import ru.platform.finance.enumz.RecordType;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceHistoryRsDto {

    private String id;

    @Schema(description = "Идентификатор заказа", example = "AAAA-0000")
    private String orderId;

    @Schema(description = "Тип операции", example = "TIP")
    private RecordType recordType;

    @Schema(description = "Статус операции", example = "COMPLETED")
    private PaymentStatus paymentStatus;

    @Schema(description = "Сумма операции", example = "99.99")
    private BigDecimal amount;

    @Schema(description = "Дата и время (UTC) создания операции", example = "2025-06-26 12:00")
    private String createdAt;

    @Schema(description = "Дата и время (UTC) завершения операции", example = "2025-06-26 12:00")
    private String completedAt;
}
