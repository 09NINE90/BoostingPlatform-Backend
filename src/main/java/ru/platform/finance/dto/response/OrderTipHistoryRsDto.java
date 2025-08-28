package ru.platform.finance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.finance.enumz.PaymentStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
@Builder
public class OrderTipHistoryRsDto {

    @Schema(description = "Идентификатор записи", example = DEFAULT_UUID)
    private UUID id;

    @Schema(description = "Статус чаевых", example = "ON_PENDING")
    private PaymentStatus status;

    @Schema(description = "Сумма чаевых", example = "5.55")
    private BigDecimal amount;

    @Schema(description = "Дата и время (UTC) создания операции", example = "2025-06-26 12:00")
    private OffsetDateTime createdAt;
}
