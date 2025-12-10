package ru.platform.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для передачи фронту данные рефералах заказчика")
public class ReferralInfoRsDto {

    private GeneralStats generalStats;
    private List<ReferralDetail> referrals;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralStats {

        @Schema(description = "Общее количество рефералов", example = "15")
        private Integer totalReferrals;

        @Schema(description = "Количество активных рефералов (совершивших хотя бы 1 заказ)", example = "8")
        private Integer activeReferrals;

        @Schema(description = "Общий текущий баланс реферальных бонусов", example = "1250.75")
        private BigDecimal totalReferralBalance;

        @Schema(description = "Общая сумма заработанных реферальных бонусов за все время", example = "5670.50")
        private BigDecimal totalEarned;

        @Schema(description = "Общая сумма заказов всех рефералов", example = "125000.00")
        private BigDecimal totalReferralsOrderAmount;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReferralDetail {

        @Schema(description = "ID реферального отношения")
        private UUID relationId;

        @Schema(description = "ID приглашенного пользователя")
        private UUID referredUserId;

        @Schema(description = "Email приглашенного пользователя")
        private String referredUserEmail;

        @Schema(description = "Имя приглашенного пользователя")
        private String referredUserName;

        @Schema(description = "Процент реферального бонуса", example = "2.50")
        private BigDecimal referralPercentage;

        @Schema(description = "Текущий баланс по данному рефералу", example = "150.25")
        private BigDecimal referralBalance;

        @Schema(description = "Общая сумма заработка с данного реферала", example = "450.75")
        private BigDecimal totalEarned;

        @Schema(description = "Флаг активности реферала")
        private Boolean hasActivity;

        @Schema(description = "Дата создания реферального отношения")
        private OffsetDateTime createdAt;

        @Schema(description = "Дата последней активности реферала")
        private OffsetDateTime lastActivityAt;

        @Schema(description = "Количество завершенных заказов реферала", example = "5")
        private Integer completedOrdersCount;

        @Schema(description = "Общая сумма заказов реферала", example = "7500.00")
        private BigDecimal totalOrderAmount;
    }

}
