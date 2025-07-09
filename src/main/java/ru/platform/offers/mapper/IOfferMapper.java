package ru.platform.offers.mapper;

import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.OfferSectionEntity;
import ru.platform.offers.dao.SectionItemEntity;
import ru.platform.offers.dto.response.CartItemRsDto;
import ru.platform.offers.dto.response.OfferSectionItemRsDto;
import ru.platform.offers.dto.response.OfferSectionRsDto;
import ru.platform.offers.dto.response.GameOffersRsDto;

public interface IOfferMapper {

    GameOffersRsDto toOfferByGameIdRsDto(OfferEntity offer);
    OfferSectionRsDto toOfferSectionRsDto(OfferSectionEntity offerSection);
    OfferSectionItemRsDto toOfferSectionItemRsDto(SectionItemEntity sectionItemEntity);
    CartItemRsDto toOfferCartRsDto(OfferCartEntity offerCartEntity);
}
