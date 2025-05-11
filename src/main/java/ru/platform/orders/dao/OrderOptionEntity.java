package ru.platform.orders.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.offers.dao.OfferOptionEntity;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_options")
public class OrderOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private OfferOptionEntity option;

    @Column(name = "option_title")
    private String optionTitle;

    @Column(name = "value")
    private String value;

    @Column(name = "label")
    private String label;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}

