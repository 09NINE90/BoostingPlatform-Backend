package ru.platform.offers.dao.options_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "checkbox_option", schema = "dev")
public class CheckboxOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Schema(description = "Название чекбокса")
    private String name;

    @Column(name = "cost")
    @Schema(description = "Базовая стоимость чекбокса")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "types_id")
    @JsonBackReference
    private OptionTypesEntity types;
}
