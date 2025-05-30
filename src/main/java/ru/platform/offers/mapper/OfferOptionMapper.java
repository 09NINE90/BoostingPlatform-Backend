package ru.platform.offers.mapper;

import org.springframework.stereotype.Component;
import ru.platform.offers.dao.OfferOptionEntity;
import ru.platform.offers.dao.OptionItemEntity;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.OptionItemRsDto;

import java.util.*;

@Component
public class OfferOptionMapper implements IOfferOptionMapper{

    public OfferOptionRsDto toDto(OfferOptionEntity entity, Map<UUID, List<OfferOptionEntity>> subOptionsMap) {
        return new OfferOptionRsDto(
                entity.getId(),
                entity.getOptionId(),
                entity.getTitle(),
                entity.getType(),
                entity.isMultiple(),
                entity.getSliderPriceChange(),
                entity.getMin(),
                entity.getMax(),
                entity.getStep(),
                entity.getItems().stream()
                        .map(item -> toItemDto(item, subOptionsMap))
                        .toList()
        );
    }

    public OptionItemRsDto toItemDto(OptionItemEntity entity, Map<UUID, List<OfferOptionEntity>> subOptionsMap) {
        List<OfferOptionRsDto> subOptions = Optional.ofNullable(subOptionsMap.get(entity.getId()))
                .orElse(List.of())
                .stream()
                .map(subOpt -> toDto(subOpt, subOptionsMap))
                .toList();

        return new OptionItemRsDto(
                entity.getId(),
                entity.getValue(),
                entity.getLabel(),
                entity.getPriceChange(),
                entity.getTimeChange(),
                entity.getPercentChange(),
                subOptions
        );
    }
}
