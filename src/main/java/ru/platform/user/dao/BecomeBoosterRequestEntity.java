package ru.platform.user.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.user.enumz.ApplicationStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "become_booster_request")
@Schema(description = "Объект заявок на становление бустером")
public class BecomeBoosterRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "discord_tag")
    private String discordTag;

    @ElementCollection
    @CollectionTable(name = "booster_selected_games", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "game_name")
    private List<String> selectedGames;

    @Column(name = "custom_games")
    private String customGames;

    @Column(name = "gaming_experience", nullable = false, columnDefinition="TEXT")
    private String gamingExperience;

    @Column(name = "boosting_experience", nullable = false, columnDefinition="TEXT")
    private String boostingExperience;

    @Column(name = "tracker_links", columnDefinition="TEXT")
    private String trackerLinks;

    @Column(name = "progress_images", columnDefinition="TEXT")
    private String progressImages;

    @Column(name = "additional_info", columnDefinition="TEXT")
    private String additionalInfo;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;
}
