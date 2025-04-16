package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.OfferOptionEntity;
import ru.platform.offers.dao.OptionItemEntity;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.OptionItemRsDto;

@Component
public class OfferOptionMapper implements IOfferOptionMapper {

    @Override
    public OfferOptionRsDto toDto(OfferOptionEntity entity) {
        return new OfferOptionRsDto(
                entity.getId(),
                entity.getOptionId(),
                entity.getTitle(),
                entity.getType(),
                entity.isMultiple(),
                entity.getMin(),
                entity.getMax(),
                entity.getStep(),
                entity.getItems().stream().map(this::toItemDto).toList()
        );
    }

    @Override
    public OptionItemRsDto toItemDto(OptionItemEntity entity) {
        return new OptionItemRsDto(
                entity.getId(),
                entity.getValue(),
                entity.getLabel(),
                entity.getPriceChange(),
                entity.getTimeChange(),
                entity.getPercentChange(),
                entity.getSubOptions().stream().map(this::toDto).toList()
        );
    }
}
