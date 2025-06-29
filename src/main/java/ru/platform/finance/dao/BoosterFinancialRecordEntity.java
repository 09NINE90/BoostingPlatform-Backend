package ru.platform.finance.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.finance.enumz.PaymentStatus;
import ru.platform.finance.enumz.RecordType;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.user.dao.UserEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booster_financial_records",
        indexes = {
                @Index(name = "idx_booster_status_calculated", columnList = "booster_id,status,calculated"),
                @Index(name = "idx_completed_calculated", columnList = "status,calculated,completed_at")
        })
public class BoosterFinancialRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Заказ, к которому относится запись
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    /**
     * Бустер, которому принадлежит запись
     */
    @ManyToOne
    @JoinColumn(name = "booster_id")
    private UserEntity booster;

    /**
     * Типы операций
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "record_type")
    private RecordType recordType;

    /**
     * Сумма для начисления или вывода
     */
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * Статусы операций
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    /**
     * Значение даты и времени, когда запись создается
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    /**
     * Значение даты и времени, когда запись принимает статус COMPLETED
     */
    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    /**
     * Значение 'ФЛАГ' для понимания посчитана запись или нет default = false
     */
    @Column(name = "calculated")
    private boolean calculated;
}
