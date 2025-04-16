package ru.platform.offers.mapper;

import ru.platform.offers.dao.OfferOptionEntity;
import ru.platform.offers.dao.OptionItemEntity;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.OptionItemRsDto;

public interface IOfferOptionMapper {

    OfferOptionRsDto toDto(OfferOptionEntity entity);
    OptionItemRsDto toItemDto(OptionItemEntity entity);
}
