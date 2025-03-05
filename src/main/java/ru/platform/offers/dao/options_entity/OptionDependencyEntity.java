package ru.platform.offers.dao.options_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "option_dependency")
public class OptionDependencyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "source_option_id")
    private long sourceOptionId;

    @Column(name = "target_option_id")
    private long targetOptionId;

    @Column(name = "effect")
    private double effect;
}
