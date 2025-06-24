package ru.platform.user.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.BoosterLevelName;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booster_profile")
@Schema(description = "Объект профиля бустера")
public class BoosterProfileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID профиля бустера")
    private UUID id;

    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private BoosterLevelName level = BoosterLevelName.ROOKIE;

    @Column(name = "number_of_completed_orders", nullable = false)
    private Integer numberOfCompletedOrders;

    @Column(name = "percentage_of_order")
    @Schema(description = "Процент с заказа бустера")
    private Double percentageOfOrder;

    @Column(name = "balance", precision = 19, scale = 4)
    @Schema(description = "Баланс бустера")
    private BigDecimal balance;

    @Column(name = "total_income", precision = 19, scale = 4)
    @Schema(description = "Суммарный заработок бустера")
    private BigDecimal totalIncome;

    @Column(name = "total_tips", precision = 19, scale = 4)
    @Schema(description = "Сумма чаевых бустера")
    private BigDecimal totalTips;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;
}
