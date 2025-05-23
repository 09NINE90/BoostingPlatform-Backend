package ru.platform.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.entity.options_entity.OptionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "services", schema = "dev")
@Schema(description = "Объект в котором хранятся заказы, созданные админом платформы")
public class OrderServicesEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID заказа")
    private UUID id;
    @Column(name = "second_id")
    @Schema(description = "ID заказа для отображения на странице")
    private String secondId;
    @Column(name = "image_url", columnDefinition="TEXT")
    @Schema(description = "Ссылка на изображение для игры")
    private String imageUrl;
    @Column(name = "title")
    @Schema(description = "Название заказа")
    private String title;
    @Column(name = "description", columnDefinition="TEXT")
    @Schema(description = "Описание заказа")
    private String description;
    @Column(name = "base_price", scale = 2)
    @Schema(description = "Базовая стоимость заказа")
    private Float basePrice;
    @Column(name = "categories")
    private String categories;
    @Column(name = "created_at")
    @Schema(description = "Дата создания заказа на платформе")
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;
    @ManyToOne
    @JoinColumn(name = "game_id")
    @Schema(description = "Игра по которой заказ")
    private GameEntity game;
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OptionEntity> options;

}
