package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.*;
import ru.platform.offers.dto.response.OfferCartRsDto;
import ru.platform.offers.dto.response.OfferSectionItemRsDto;
import ru.platform.offers.dto.response.OfferSectionRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.utils.DtoUtil;

import java.util.List;

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

    @Override
    public OfferCartRsDto toOfferCartRsDto(OfferCartEntity offerCartEntity) {
        return OfferCartRsDto.builder()
                .offerId(offerCartEntity.getOffer().getId())
                .gameName(offerCartEntity.getGameName())
                .offerName(offerCartEntity.getOffer().getTitle())
                .totalPrice(offerCartEntity.getTotalPrice())
                .totalTime(offerCartEntity.getTotalTime())
                .selectedOptions(toSelectionOptionsCartList(offerCartEntity.getOptionCarts()))
                .build();
    }

    private List<OfferCartRsDto.SelectedOptionToCartDto> toSelectionOptionsCartList(List<OfferOptionCartEntity> optionCarts) {
        if (optionCarts == null || optionCarts.isEmpty()) return null;
        return optionCarts.stream().map(this::toSelectionOptionsCart).toList();
    }

    private OfferCartRsDto.SelectedOptionToCartDto toSelectionOptionsCart(OfferOptionCartEntity offerOptionCartEntity) {
        return OfferCartRsDto.SelectedOptionToCartDto.builder()
                .label(offerOptionCartEntity.getLabel())
                .value(offerOptionCartEntity.getValue())
                .optionTitle(offerOptionCartEntity.getOptionTitle())
                .build();
    }
}
