package ru.platform.games.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.dao.BoosterProfileEntity;
import ru.platform.user.dao.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game_tags")
public class GameTag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booster_profile_id", nullable = false)
    private BoosterProfileEntity boosterProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_admin_id")
    private UserEntity verifiedBy;

    @Column(name = "verified_by_admin", nullable = false)
    private boolean isVerified;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "verified_at")
    private OffsetDateTime verifiedAt;

}
