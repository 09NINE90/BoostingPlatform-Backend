package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.OfferSectionEntity;
import ru.platform.offers.dao.SectionItemEntity;
import ru.platform.offers.dto.response.OfferSectionItemRsDto;
import ru.platform.offers.dto.response.OfferSectionRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;

@Component
public class OfferMapper implements IOfferMapper {

    @Override
    public OffersByGameIdRsDto toOfferByGameIdRsDto(OfferEntity offer) {
        return OffersByGameIdRsDto.builder()
                .offerId(offer.getId().toString())
                .offerTitle(offer.getTitle())
                .offerDescription(offer.getDescription())
                .offerImageUrl(offer.getImageUrl())
                .offerPrice(offer.getPrice())
                .build();
    }

    @Override
    public OfferSectionRsDto toOfferSectionRsDto(OfferSectionEntity offerSection) {
        return OfferSectionRsDto.builder()
                .title(offerSection.getTitle())
                .type(offerSection.getType())
                .description(offerSection.getDescription())
                .items(offerSection.getItems().stream().map(this::toOfferSectionItemRsDto).toList())
                .build();
    }

    @Override
    public OfferSectionItemRsDto toOfferSectionItemRsDto(SectionItemEntity sectionItemEntity) {
        if (!sectionItemEntity.getItems().isEmpty()) {
            return OfferSectionItemRsDto.builder()
                    .title(sectionItemEntity.getTitle())
                    .type(sectionItemEntity.getType())
                    .description(sectionItemEntity.getDescription())
                    .items(sectionItemEntity.getItems().stream().map(this::toOfferSectionItemRsDto).toList())
                    .relatedOffer(OfferSectionItemRsDto.RelatedOfferDto.builder()
                            .title(sectionItemEntity.getTitle())
                            .imageUrl(sectionItemEntity.getImageUrl())
                            .price(sectionItemEntity.getPrice())
                            .offerId(sectionItemEntity.getRelatedOfferId() == null ? null : sectionItemEntity.getRelatedOfferId().toString())
                            .build())
                    .build();
        }
        else {
            return OfferSectionItemRsDto.builder()
                    .description(sectionItemEntity.getDescription())
                    .build();
        }
    }

}
