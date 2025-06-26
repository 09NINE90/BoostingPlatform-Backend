package ru.platform.orders.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.dao.UserEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "second_id", insertable = false, updatable = false)
    private Long  secondId;

    @Column(name = "short_id", unique = true, length = 9)
    private String shortId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @Column(name = "status")
    private String status;

    @Column(name = "offer_name")
    private String offerName;

    @ManyToOne
    @JoinColumn(name = "booster")
    private UserEntity booster;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_platform")
    private String gamePlatform;

    @Column(name = "base_price", precision = 19, scale = 4)
    private BigDecimal basePrice;

    @Column(name = "total_price", precision = 19, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "booster_salary", precision = 19, scale = 4)
    private BigDecimal boosterSalary;

    @Column(name = "total_time")
    private int totalTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderOptionEntity> optionList;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "start_time_execution")
    private OffsetDateTime startTimeExecution;

    @Column(name = "end_time_execution")
    private OffsetDateTime endTimeExecution;

}

