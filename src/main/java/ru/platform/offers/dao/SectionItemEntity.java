package ru.platform.offers.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.offers.enumz.ItemType;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer_section_item")
public class SectionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "related_offer_id")
    private UUID relatedOfferId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SectionItemEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionItemEntity> items;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private OfferSectionEntity section;
}
