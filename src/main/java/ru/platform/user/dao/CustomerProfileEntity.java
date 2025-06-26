package ru.platform.user.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.CustomerStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_profile")
@Schema(description = "Объект профиля заказчика")
public class CustomerProfileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cashback_balance", precision = 19, scale = 4)
    private BigDecimal cashbackBalance = BigDecimal.ZERO;

    @Column(name = "discount_percentage")
    private Integer discountPercentage = 1;

    @Column(name = "total_orders")
    private Integer totalOrders = 0;

    @Column(name = "total_amount_of_orders")
    private BigDecimal totalAmountOfOrders;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.EXPLORER;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;
}
