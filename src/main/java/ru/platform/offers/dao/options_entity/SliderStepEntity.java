package ru.platform.offers.dao.options_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slider_step", schema = "dev")
public class SliderStepEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    private String value;

    @Column(name = "cost")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "slider_id")
    @JsonBackReference
    private SliderOptionEntity slider;
}
