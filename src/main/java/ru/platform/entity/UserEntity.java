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
 * Объект пользователя
 */
@Entity
@Builder
@Data
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

    @Column(name = "nickname")
    @Schema(description = "Имя пользователя")
    private String nickname;

    @Column(name = "roles")
    private String roles;

    @Column(name = "second_id")
    @Schema(description = "ID пользователя для отображения на странице")
    private String secondId;

    @Column(name = "username")
    @Schema(description = "Email пользователя")
    private String username;

    @Column(name = "password")
    @Schema(description = "Пароль пользователя")
    private String password;

    @Column(name = "rating")
    @Schema(description = "Уровень скила пользователя")
    private String rating;

    @Column(name = "orders_count")
    @Schema(description = "Общее количество выполненых/купленных заказов")
    private int ordersCount;

    @Column(name = "created_at")
    @Schema(description = "Дата создания аккаунта на платформе")
    private LocalDate createdAt;

    @Column(name = "last_activity_at")
    @Schema(description = "Дата создания последей активности на платформе")
    private LocalDate lastActivityAt;
}
