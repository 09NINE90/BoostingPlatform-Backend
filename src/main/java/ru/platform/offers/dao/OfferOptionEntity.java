package ru.platform.offers.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.offers.enumz.OfferOptionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offer_options")
public class OfferOptionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private OfferEntity offer;

    @Column(name = "option_id")
    private String optionId;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OfferOptionType type;

    @Column(name = "multiple")
    private boolean multiple;

    @Column(name = "slider_price_change")
    private Double sliderPriceChange;

    @Column(name = "time_change")
    private Integer timeChange;

    @Column(name = "min")
    private Integer min;

    @Column(name = "max")
    private Integer max;

    @Column(name = "step")
    private Integer step;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionItemEntity> items;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferOptionCartEntity> optionCart;

    @ManyToOne
    @JoinColumn(name = "parent_item_id")
    private OptionItemEntity parentItem;
}
