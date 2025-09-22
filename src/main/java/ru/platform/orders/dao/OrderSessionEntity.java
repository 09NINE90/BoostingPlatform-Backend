package ru.platform.orders.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.orders.enumz.SessionStatus;
import ru.platform.user.dao.UserEntity;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_session")
public class OrderSessionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заказ, к которому относится сессия
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    /**
     * Пользователь, запустивший сессию
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Заявленная продолжительность сессии в часах
     */
    @Column(name = "duration", nullable = false)
    private int duration;

    /**
     * Фактическая продолжительность сессии
     */
    @Column(name = "fact_duration")
    private String factDuration;

    /**
     * Описание выполненных работ
     */
    @Column(name = "progress_message", columnDefinition="TEXT")
    private String progressMessage;

    /**
     * Ссылка на imgur (по прогрессу)
     */
    @Column(name = "imgur_link", columnDefinition="TEXT")
    private String imgurLink;

    /**
     * Ссылка на стрим (если есть)
     */
    @Column(name = "stream_link", columnDefinition="TEXT")
    private String streamLink;

    /**
     * Статус сессии
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    /**
     * Дата и время начала сессии
     */
    @Column(name = "start_date_time", nullable = false)
    private OffsetDateTime startDateTime;

    /**
     * Дата и время окончания сессии
     */
    @Column(name = "end_date_time")
    private OffsetDateTime endDateTime;

    /**
     * Дата создания записи
     */
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    /**
     * Дата последнего обновления
     */
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        startDateTime = OffsetDateTime.now();
        if (status == null) {
            status = SessionStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
