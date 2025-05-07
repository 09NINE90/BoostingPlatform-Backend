package ru.platform.offers.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer_option_cart")
@Schema(description = "Опиции предложений в корзине")
public class OfferOptionCartEntity {

    @Id
    @Column(name = "id")
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
    @JoinColumn(name = "offer_cart_id")
    private OfferCartEntity offerCart;

}
