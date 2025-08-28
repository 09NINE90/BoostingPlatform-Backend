package ru.platform.offers.service;

import ru.platform.offers.dto.response.OfferOptionRsDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с дополнительными опциями предложений.
 */
public interface IOfferOptionService {

    /**
     * Возвращает список доступных опций для указанного предложения.
     */
    List<OfferOptionRsDto> getOptionsByOfferId(UUID offerId);
}
