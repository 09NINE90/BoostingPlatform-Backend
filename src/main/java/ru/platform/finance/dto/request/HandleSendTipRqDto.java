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
    private UUID orderId;

    @NotNull
    @Schema(description = "Сумма чаевых бустеру за заказ", example = "9.99")
    private BigDecimal tipAmount;
}
