package ru.platform.entity.options_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slider_option", schema = "dev")
public class SliderOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_value")
    private int minValue;

    @Column(name = "max_value")
    private int maxValue;

    @OneToMany(mappedBy = "slider", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SliderStepEntity> steps;

    @ManyToOne
    @JoinColumn(name = "types_id")
    @JsonBackReference
    private OptionTypesEntity types;

}
