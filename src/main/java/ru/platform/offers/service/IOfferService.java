package ru.platform.offers.service;

import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.dto.request.OfferToCartRqDto;
import ru.platform.offers.dto.response.OfferByIdRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.offers.dto.response.OffersListRsDto;

import java.util.List;
import java.util.UUID;

public interface IOfferService {

    OffersListRsDto<OffersByGameIdRsDto> getOffersByRequest(OfferRqDto request);
    List<OffersByGameIdRsDto> getOffersByGameId(UUID gameId);
    OfferByIdRsDto getOfferById(UUID offerId);
    void addOfferToCart(OfferToCartRqDto offer);
}
