package ru.platform.finance.mapper;

import org.springframework.stereotype.Component;
import ru.platform.finance.dao.BoosterFinancialRecordEntity;
import ru.platform.finance.dto.response.BalanceHistoryRsDto;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.utils.DateTimeUtils;
import ru.platform.utils.GenerateSecondIdUtil;

import java.math.BigDecimal;

import static ru.platform.finance.enumz.RecordType.WITHDRAWAL;

@Component
public class BalanceMapper {

    public BalanceHistoryRsDto toBalanceHistoryRsDto(BoosterFinancialRecordEntity entity){
        return BalanceHistoryRsDto.builder()
                .id(entity.getId().toString())
                .orderId(getOrderId(entity.getOrder()))
                .recordType(entity.getRecordType())
                .paymentStatus(entity.getStatus())
                .amount(getAmount(entity))
                .createdAt(DateTimeUtils.offsetDateTimeToStringUTC(entity.getCreatedAt()))
                .completedAt(DateTimeUtils.offsetDateTimeToStringUTC(entity.getCompletedAt()))
                .build();
    }

    private String getOrderId(OrderEntity order) {
        if (order == null) return null;
        return GenerateSecondIdUtil.toRandomLookingId(order.getSecondId());
    }

    private BigDecimal getAmount(BoosterFinancialRecordEntity entity) {
        if (entity.getRecordType().equals(WITHDRAWAL)){
            return BigDecimal.ZERO.subtract(entity.getAmount());
        }
        return entity.getAmount();
    }
}
