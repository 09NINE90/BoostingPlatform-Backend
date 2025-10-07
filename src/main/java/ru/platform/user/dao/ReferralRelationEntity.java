package ru.platform.user.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.UserType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "referral_relations",
        indexes = {
                @Index(name = "idx_referrer_id", columnList = "referrer_id"),
                @Index(name = "idx_referred_id", columnList = "referred_id"),
                @Index(name = "idx_created_at", columnList = "created_at")
        })
@Schema(description = "Реферальные отношения между пользователями")
public class ReferralRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID реферального отношения")
    private UUID id;

    /**
     * Пользователь, который пригласил (реферер)
     * ManyToOne связь с UserEntity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referrer_id", nullable = false)
    @Schema(description = "Пользователь-пригласитель")
    private UserEntity referrer;

    /**
     * Приглашенный пользователь (реферал)
     * OneToOne связь с UserEntity (уникальный)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_id", nullable = false, unique = true)
    @Schema(description = "Приглашенный пользователь")
    private UserEntity referred;

    /**
     * Тип приглашенного пользователя
     * Определяет логику начисления бонусов
     */
    @Column(name = "referred_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Тип приглашенного пользователя")
    private UserType referredType;

    /**
     * Процент реферального бонуса для приглашенных пользователей
     * Определяет размер вознаграждения от суммы заказов реферала
     * Значение по умолчанию: 0.02 (2.00%)
     * Может быть изменен администратором для индивидуальных условий
     * Диапазон значений: от 0.00% до 50.00%
     */
    @Column(name = "referral_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal referralPercentage;

    /**
     * Текущий баланс реферальных бонусов
     * Накопительная сумма доступная для использования реферером
     * Начисляется при завершении заказов приглашенными пользователями
     * Может быть использована для выводов или оплаты услуг
     * Значение не может быть отрицательным
     */
    @Column(name = "referral_balance", precision = 19, scale = 4, nullable = false)
    private BigDecimal referralBalance;

    /**
     * Дата создания реферального отношения
     */
    @Column(name = "created_at", nullable = false)
    @Schema(description = "Дата создания отношения")
    private OffsetDateTime createdAt;

    /**
     * Флаг активности реферала
     * TRUE если реферал совершил хотя бы 1 успешный заказ
     */
    @Column(name = "has_activity")
    @Schema(description = "Флаг активности реферала")
    private Boolean hasActivity;

    /**
     * Общая сумма заработанная с данного реферала
     */
    @Column(name = "total_earned", precision = 19, scale = 4)
    @Schema(description = "Сумма заработка с реферала")
    private BigDecimal totalEarned;

    /**
     * Дата последней активности реферала
     * (последний завершенный заказ)
     */
    @Column(name = "last_activity_at")
    @Schema(description = "Дата последней активности")
    private OffsetDateTime lastActivityAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

}