package ru.platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile", schema = "dev")
@Schema(description = "Объект дополнительных данных пользователя")
public class UserProfileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "second_id")
    @Schema(description = "ID пользователя для отображения на странице")
    private String secondId;

    @Column(name = "nickname")
    @Schema(description = "Имя пользователя")
    private String nickname;

    @Column(name = "created_at")
    @Schema(description = "Дата создания аккаунта на платформе")
    private LocalDate createdAt;

    @Column(name = "last_activity_at")
    @Schema(description = "Дата создания последей активности на платформе")
    private LocalDate lastActivityAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

}
