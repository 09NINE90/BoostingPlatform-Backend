package ru.platform.offers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.offers.dao.repository.OfferOptionRepository;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.mapper.IOfferOptionMapper;
import ru.platform.offers.service.IOfferOptionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferOptionService implements IOfferOptionService {

    private final OfferOptionRepository offerOptionRepository;
    private final IOfferOptionMapper mapper;

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.OFFER_OPTIONS)
    public List<OfferOptionRsDto> getOptionsByOfferId(UUID offerId) {
        return offerOptionRepository.findAllByOfferId(offerId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
