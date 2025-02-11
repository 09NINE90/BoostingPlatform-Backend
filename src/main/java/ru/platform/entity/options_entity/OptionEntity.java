package ru.platform.entity.options_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.entity.OrderServicesEntity;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "option", schema = "dev")
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

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private OrderServicesEntity service;

}
