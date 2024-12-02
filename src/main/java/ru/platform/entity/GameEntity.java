package ru.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Объект в котором хранятся игры для бустинга
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games", schema = "dev")
@Schema(description = "Объект в котором хранятся игры для бустинга")
public class GameEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID игры")
    private UUID id;

    @Column(name = "title")
    @JsonProperty("title")
    @Schema(description = "Название игры")
    private String title;

    @Column(name = "description")
    @JsonProperty("description")
    @Schema(description = "Описание игры")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private List<CategoryEntity> categories;

}
