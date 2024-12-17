package ru.platform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Объект который хранит данные об уже
 * - созданных клиентом;
 * - взятых в работу;
 * - выполненых заказах;
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_by_customers", schema = "dev")
@Schema(description = "Объект который хранит данные об уже\n" +
        " - созданных клиентом;\n" +
        " - взятых в работу;\n" +
        " - выполненых заказах;")
public class OrdersByCustomersEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "second_id")
    private String secondId;

    @Column(name = "description")
    @Schema(description = "Описание созданого заказа")
    private String description;

    @Column(name = "price")
    @Schema(description = "Предложенная стоимость заказа")
    private float price;

    @Column(name = "status")
    @Schema(description = "Предложенная стоимость заказа")
    private String status;

    @Column(name = "platform")
    @Schema(description = "Платформа выполнения (ПК, PS, Xbox)")
    private String platform;

    @Column(name = "is_self_play")
    @Schema(description = "Заказ выполняется в одиночку или с клиентом")
    private boolean isSelfPlay;

    @Column(name = "tome_to_start")
    @Schema(description = "Время за которое нужно начать выполнение заказа")
    private String timeToStart;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "base_order_id")
    private ServicesEntity baseOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private UserEntity worker;
}
