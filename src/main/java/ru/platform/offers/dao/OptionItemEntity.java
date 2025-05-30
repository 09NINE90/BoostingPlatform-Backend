package ru.platform.offers.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "option_items")
public class OptionItemEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private OfferOptionEntity option;

    @Column(name = "value")
    private String value;

    @Column(name = "label")
    private String label;

    @Column(name = "price_change")
    private Double priceChange;

    @Column(name = "time_change")
    private Integer timeChange;

    @Column(name = "percent_change")
    private Double percentChange;

    @OneToMany(mappedBy = "parentItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferOptionEntity> subOptions = new ArrayList<>();
}
