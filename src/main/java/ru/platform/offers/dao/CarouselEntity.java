package ru.platform.offers.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "home_carousel")
@Schema(description = "Карусель на главной странице")
public class CarouselEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID объекта карусели")
    private long id;

    @Column(name = "title")
    @Schema(description = "Заголовок карусели")
    private String title;

    @Column(name = "description")
    @Schema(description = "Описание карусели")
    private String description;

    @Column(name = "image_url", columnDefinition="TEXT")
    @Schema(description = "Ссылка на картинку карусели")
    private String imageUrl;

    @Column(name = "is_active")
    @Schema(description = "Флаг для того, чтобы указыать на то, нужно ли показывать объект")
    private boolean isActive;

}
