package ru.platform.finance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class HandleWithdrawalRqDto {

    @Schema(description = "Сумма для запроса на вывод средств бустера", example = "99.99")
    private BigDecimal withdrawalAmount;
}
