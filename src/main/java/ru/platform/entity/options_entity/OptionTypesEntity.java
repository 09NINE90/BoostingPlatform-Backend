package ru.platform.entity.options_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "option_types", schema = "dev")
public class OptionTypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "types", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SliderOptionEntity> slider;

    @OneToMany(mappedBy = "types", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CheckboxOptionEntity> checkbox;

    @ManyToOne
    @JoinColumn(name = "option_id")
    @JsonBackReference
    private OptionEntity option;
}
