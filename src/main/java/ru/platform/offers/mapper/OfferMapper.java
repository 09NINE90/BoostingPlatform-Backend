package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.games.dto.response.PlatformDto;
import ru.platform.offers.dao.*;
import ru.platform.offers.dto.response.CartItemRsDto;
import ru.platform.offers.dto.response.OfferSectionItemRsDto;
import ru.platform.offers.dto.response.OfferSectionRsDto;
import ru.platform.offers.dto.response.GameOffersRsDto;
import ru.platform.utils.DtoUtil;

import java.util.List;

@Component
public class OfferMapper implements IOfferMapper {

    @Override
    public GameOffersRsDto toOfferByGameIdRsDto(OfferEntity offer) {
        return GameOffersRsDto.builder()
                .id(offer.getId())
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
    public CartItemRsDto toOfferCartRsDto(OfferCartEntity offerCartEntity) {
        return CartItemRsDto.builder()
                .id(offerCartEntity.getId())
                .offerId(offerCartEntity.getOffer().getId())
                .gameName(offerCartEntity.getGame().getTitle())
                .gamePlatform(PlatformDto.builder()
                        .id(offerCartEntity.getGamePlatform().getId())
                        .title(offerCartEntity.getGamePlatform().getTitle())
                        .name(offerCartEntity.getGamePlatform().getName())
                        .build()
                )
                .offerName(offerCartEntity.getOffer().getTitle())
                .basePrice(offerCartEntity.getBasePrice())
                .totalPrice(offerCartEntity.getTotalPrice())
                .totalTime(offerCartEntity.getTotalTime())
                .selectedOptions(toSelectionOptionsCartList(offerCartEntity.getOptionCarts()))
                .build();
    }

    private List<CartItemRsDto.SelectedOptionToCartDto> toSelectionOptionsCartList(List<OfferOptionCartEntity> optionCarts) {
        if (optionCarts == null || optionCarts.isEmpty()) return null;
        return optionCarts.stream().map(this::toSelectionOptionsCart).toList();
    }

    private CartItemRsDto.SelectedOptionToCartDto toSelectionOptionsCart(OfferOptionCartEntity offerOptionCartEntity) {
        return CartItemRsDto.SelectedOptionToCartDto.builder()
                .label(offerOptionCartEntity.getLabel())
                .value(offerOptionCartEntity.getValue())
                .optionTitle(offerOptionCartEntity.getOptionTitle())
                .build();
    }
}
