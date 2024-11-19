package ru.platform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Объект в котором хранятся игры для бустинга
 */
@Builder
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
    @Schema(description = "Название игры")
    private String title;

    @Column(name = "description")
    @Schema(description = "Описание игры")
    private String description;
}
