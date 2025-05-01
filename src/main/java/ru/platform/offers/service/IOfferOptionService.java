package ru.platform.offers.service;

import ru.platform.offers.dto.response.OfferOptionRsDto;

import java.util.List;
import java.util.UUID;

public interface IOfferOptionService {

    List<OfferOptionRsDto> getOptionsByOfferId(UUID offerId);
}
