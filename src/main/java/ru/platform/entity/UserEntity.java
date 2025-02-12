package ru.platform.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "user", schema = "dev")
@Schema(description = "Объект пользователя")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID пользователя")
    private UUID id;

    @Column(name = "username")
    @Schema(description = "Email пользователя")
    private String username;

    @Column(name = "password")
    @Schema(description = "Пароль пользователя")
    private String password;

    @Column(name = "roles")
    private String roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserProfileEntity profile;
}
