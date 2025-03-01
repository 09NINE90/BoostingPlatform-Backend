package ru.platform.offers.mapper;

import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;

public interface IOfferMapper {

    OffersByGameIdRsDto toOfferByGameIdRsDto(OfferEntity offer);

}
