package ru.platform.finance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class HandleSendTipRqDto {

    @NotNull
    @Schema(description = "Идентификатор заказа", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID orderId;

    @NotNull
    @Schema(description = "Сумма чаевых бустеру за заказ", example = "9.99")
    private BigDecimal tipAmount;
}