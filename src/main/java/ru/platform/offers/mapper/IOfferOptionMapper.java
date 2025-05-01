package ru.platform.offers.mapper;

import ru.platform.offers.dao.OfferOptionEntity;
import ru.platform.offers.dao.OptionItemEntity;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.OptionItemRsDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IOfferOptionMapper {

    OfferOptionRsDto toDto(OfferOptionEntity entity, Map<UUID, List<OfferOptionEntity>> subOptionsMap);
    OptionItemRsDto toItemDto(OptionItemEntity entity, Map<UUID, List<OfferOptionEntity>> subOptionsMap);
}
