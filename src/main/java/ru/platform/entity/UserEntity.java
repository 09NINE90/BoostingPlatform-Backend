package ru.platform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Объект пользователя
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "dev")
@Schema(description = "Объект пользователя")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID пользователя")
    private UUID id;

    @Column(name = "second_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID пользователя для отображения на странице")
    private long secondId;

    @Column(name = "email")
    @Schema(description = "Email пользователя")
    private String email;

    @Column(name = "password")
    @Schema(description = "Пароль пользователя")
    private String password;

    @Column(name = "rating")
    @Schema(description = "Уровень скила пользователя")
    private String rating;

    @Column(name = "orders_count")
    @Schema(description = "Общее количество выполненых заказов")
    private int ordersCount;

    @Column(name = "created_at")
    @Schema(description = "Дата создания аккаунта на платформе")
    private LocalDate createdAt;

    @Column(name = "last_activity_at")
    @Schema(description = "Дата создания последей активности на платформе")
    private LocalDate lastActivityAt;
}
