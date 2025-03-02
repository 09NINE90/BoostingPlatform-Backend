package ru.platform.offers.dao.options_entity;

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
@Table(name = "option")
public class OptionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OptionTypesEntity> types;

}
