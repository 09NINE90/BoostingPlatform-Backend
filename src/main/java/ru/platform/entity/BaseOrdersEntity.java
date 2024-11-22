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
 * Объект в котором хранятся заказы,
 * созданные админом платформы
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "base_orders", schema = "dev")
@Schema(description = "Объект в котором хранятся заказы, созданные админом платформы")
public class BaseOrdersEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID заказа")
    private UUID id;

    @Column(name = "second_id")
    @Schema(description = "ID заказа для отображения на странице")
    private String secondId;

    @Column(name = "title")
    @Schema(description = "Название заказа")
    private String title;

    @Column(name = "description")
    @Schema(description = "Описание заказа")
    private String description;

    @Column(name = "base_price")
    @Schema(description = "Базовая стоимость заказа")
    private float basePrice;

    @Column(name = "created_at")
    @Schema(description = "Дата создания заказа на платформе")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @Schema(description = "Игра по которой заказ")
    private GameEntity game;
}
