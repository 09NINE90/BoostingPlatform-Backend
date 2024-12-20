package ru.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Объект для отслеживания количесва заказов,
 * выполненых за неделю конкретным пользователем
 * для того, чтобы динамически изменять уровень активности пользователя
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_per_week", schema = "dev")
public class OrdersPerWeekEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "orders_count_per_peek")
    private int ordersCountPerWeek;
    @Column(name = "count_days_of_week")
    private Short countDaysOfWeek;
    @Column(name = "last_date")
    private Date lastDate;
    @Column(name = "week_start")
    private Date weekStart;
    @Column(name = "week_end")
    private Date weekEnd;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
