package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.OfferSectionEntity;
import ru.platform.offers.dao.SectionItemEntity;
import ru.platform.offers.dto.response.OfferSectionItemRsDto;
import ru.platform.offers.dto.response.OfferSectionRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.utils.DtoUtil;

@Component
public class OfferMapper implements IOfferMapper {

    @Override
    public OffersByGameIdRsDto toOfferByGameIdRsDto(OfferEntity offer) {
        return OffersByGameIdRsDto.builder()
                .id(offer.getId().toString())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .imageUrl(offer.getImageUrl())
                .price(offer.getPrice())
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
                            .offerId(getRelatedOfferDto(sectionItemEntity))
                            .build())
                    .build();
        } else {
            return OfferSectionItemRsDto.builder()
                    .title(sectionItemEntity.getTitle())
                    .description(sectionItemEntity.getDescription())
                    .type(sectionItemEntity.getType())
                    .relatedOffer(OfferSectionItemRsDto.RelatedOfferDto.builder()
                            .offerId(getRelatedOfferDto(sectionItemEntity))
                            .build())
                    .build();
        }
    }

    private String getRelatedOfferDto(SectionItemEntity sectionItemEntity) {
        return DtoUtil.safelyGet(sectionItemEntity, SectionItemEntity::getRelatedOfferId) == null
                ? null
                : sectionItemEntity.getRelatedOfferId().toString();
    }
}
