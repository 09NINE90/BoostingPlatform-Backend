package ru.platform.user.mapper;

import org.springframework.stereotype.Component;
import ru.platform.user.dao.ReferralRelationEntity;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.dto.response.ReferralInfoRsDto;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class ReferralInfoMapper {

    public ReferralInfoRsDto mapToReferralInfoDto(List<ReferralRelationEntity> relationEntities) {
        if (relationEntities == null || relationEntities.isEmpty()) {
            return createEmptyDto();
        }

        List<ReferralInfoRsDto.ReferralDetail> referralDetails = relationEntities.stream()
                .map(this::mapToReferralDetail)
                .toList();

        ReferralInfoRsDto.GeneralStats generalStats = calculateGeneralStats(relationEntities, referralDetails);

        return new ReferralInfoRsDto(generalStats, referralDetails);
    }

    private ReferralInfoRsDto.ReferralDetail mapToReferralDetail(ReferralRelationEntity entity) {
        UserEntity referredUser = entity.getReferred();

        return ReferralInfoRsDto.ReferralDetail.builder()
                .relationId(entity.getId())
                .referredUserId(referredUser.getId())
                .referredUserEmail(referredUser.getUsername())
                .referredUserName(referredUser.getProfile().getNickname())
                .referralPercentage(entity.getReferralPercentage())
                .referralBalance(entity.getReferralBalance() != null ? entity.getReferralBalance() : BigDecimal.ZERO)
                .totalEarned(entity.getTotalEarned() != null ? entity.getTotalEarned() : BigDecimal.ZERO)
                .hasActivity(entity.getHasActivity() != null ? entity.getHasActivity() : false)
                .createdAt(entity.getCreatedAt())
                .lastActivityAt(entity.getLastActivityAt())
                .completedOrdersCount(calculateCompletedOrdersCount(referredUser)) // нужно реализовать
                .totalOrderAmount(calculateTotalOrderAmount(referredUser)) // нужно реализовать
                .build();
    }

    private ReferralInfoRsDto.GeneralStats calculateGeneralStats(
            List<ReferralRelationEntity> entities,
            List<ReferralInfoRsDto.ReferralDetail> details) {

        int totalReferrals = entities.size();
        int activeReferrals = (int) entities.stream()
                .filter(entity -> Boolean.TRUE.equals(entity.getHasActivity()))
                .count();

        BigDecimal totalReferralBalance = entities.stream()
                .map(entity -> entity.getReferralBalance() != null ? entity.getReferralBalance() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalEarned = entities.stream()
                .map(entity -> entity.getTotalEarned() != null ? entity.getTotalEarned() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReferralsOrderAmount = details.stream()
                .map(ReferralInfoRsDto.ReferralDetail::getTotalOrderAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ReferralInfoRsDto.GeneralStats.builder()
                .totalReferrals(totalReferrals)
                .activeReferrals(activeReferrals)
                .totalReferralBalance(totalReferralBalance)
                .totalEarned(totalEarned)
                .totalReferralsOrderAmount(totalReferralsOrderAmount)
                .build();
    }

    private ReferralInfoRsDto createEmptyDto() {
        ReferralInfoRsDto.GeneralStats emptyStats = ReferralInfoRsDto.GeneralStats.builder()
                .totalReferrals(0)
                .activeReferrals(0)
                .totalReferralBalance(BigDecimal.ZERO)
                .totalEarned(BigDecimal.ZERO)
                .totalReferralsOrderAmount(BigDecimal.ZERO)
                .build();

        return new ReferralInfoRsDto(emptyStats, emptyList());
    }


    private Integer calculateCompletedOrdersCount(UserEntity user) {
        return user.getCustomerProfile().getTotalOrders();
    }

    private BigDecimal calculateTotalOrderAmount(UserEntity user) {
        return user.getCustomerProfile().getTotalAmountOfOrders();
    }
}
