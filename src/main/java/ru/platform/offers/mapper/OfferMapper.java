package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;

@Component
public class OfferMapper implements IOfferMapper {

    @Override
    public OffersByGameIdRsDto toOfferByGameIdRsDto(OfferEntity offer) {
        return OffersByGameIdRsDto.builder()
                .offerId(offer.getId().toString())
                .offerTitle(offer.getTitle())
                .offerDescription(offer.getDescription())
                .offerImageUrl(offer.getImageUrl())
                .offerPrice(offer.getPrice())
                .build();
    }

}
