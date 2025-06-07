package ru.platform.games.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import ru.platform.user.dao.UserEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
@Schema(description = "Объект в котором хранятся игры для бустинга")
public class GameEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID игры")
    private UUID id;

    @Column(name = "second_id")
    @Schema(description = "Второй id для фрона состояший из названия игры")
    private String secondId;

    @Column(name = "title")
    @Schema(description = "Название игры")
    private String title;

    @Column(name = "platforms")
    @Schema(description = "Платформы для прохождения (PS, PC, Xbox)")
    private String platforms;

    @Column(name = "image_url", columnDefinition="TEXT")
    @Schema(description = "Ссылка на изображение для игры")
    private String imageUrl;

    @Column(name = "mini_image_url", columnDefinition="TEXT")
    @Schema(description = "Ссылка на изображение игры для side bar")
    private String miniImageUrl;

    @Column(name = "description", columnDefinition="TEXT")
    @JsonProperty("description")
    @Schema(description = "Описание игры")
    private String description;

    @Column(name = "rating")
    @Schema(description = "Значение популярности игры для сортировки")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private List<CategoryEntity> categories;

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", name='" + title + '\'' +
                '}';
    }
}
