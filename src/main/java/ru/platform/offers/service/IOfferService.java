package ru.platform.offers.service;

import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.dto.response.OfferByIdRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.offers.dto.response.OffersListRsDto;

import java.util.UUID;

public interface IOfferService {

    OffersListRsDto<OffersByGameIdRsDto> getOffersByRequest(OfferRqDto request);
    OffersListRsDto<OffersByGameIdRsDto> getOffersByGameId(UUID gameId);
    OfferByIdRsDto getOfferById(UUID offerId);
}
