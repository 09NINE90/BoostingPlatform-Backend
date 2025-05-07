package ru.platform.offers.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.offers.PaginationOffersUtil;
import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.offers.dao.OfferOptionCartEntity;
import ru.platform.offers.dao.repository.OfferCartRepository;
import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.dto.request.OfferToCartRqDto;
import ru.platform.offers.dto.request.SelectedOptionToCartDto;
import ru.platform.offers.dto.response.OfferByIdRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.offers.dto.response.OffersListRsDto;
import ru.platform.offers.mapper.IOfferMapper;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.repository.OfferRepository;
import ru.platform.offers.dao.specification.OfferSpecification;
import ru.platform.offers.service.IOfferService;
import ru.platform.user.service.IAuthService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Collections.emptyList;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final OfferSpecification specification;
    private final OfferCartRepository offerCartRepository;
    private final PaginationOffersUtil paginationOffersUtil;
    private final IAuthService authService;
    private final IOfferMapper offerMapper;

    private final String LOG_PREFIX = "OfferService: ";

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.OFFERS_BY_GAME_ID)
    public List<OffersByGameIdRsDto> getOffersByGameId(UUID gameId) {
        try {
            List<OfferEntity> offersByGame = offerRepository.findAllByGameId(gameId);
            log.debug(LOG_PREFIX + "Preparing a response for the frontend");
            return offersByGame.stream()
                    .map(offerMapper::toOfferByGameIdRsDto)
                    .toList();
        } catch (Exception e) {
            log.error(LOG_PREFIX + "Error when searching for offers by game ID");
            throw new EntityNotFoundException("Error when searching for offers by game ID", e);
        }
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.OFFERS_WITH_FILTERS)
    public OffersListRsDto<OffersByGameIdRsDto> getOffersByRequest(OfferRqDto request) {
        return mapToResponse(getServicePageFunc().apply(request));
    }

    private OffersListRsDto<OffersByGameIdRsDto> mapToResponse(Page<OfferEntity> entities) {
        List<OffersByGameIdRsDto> mappedOffers = entities
                .stream()
                .map(offerMapper::toOfferByGameIdRsDto)
                .toList();

        OffersListRsDto response = new OffersListRsDto();
        response.setOffers(mappedOffers);
        response.setPageSize(entities.getSize());
        response.setPageTotal(entities.getTotalPages());
        response.setPageNumber(entities.getNumber() + 1);
        response.setRecordTotal(entities.getTotalElements());
        return response;
    }

    private Function<OfferRqDto, Page<OfferEntity>> getServicePageFunc() {
        try {
            return request -> offerRepository
                    .findAll(specification.getFilter(request), paginationOffersUtil.getPageRequest(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX + "Error when searching for offers with sorting, filters, and pagination");
            throw new EntityNotFoundException("Error when searching for offers with sorting, filters, and pagination", e);
        }

    }

    @Override
    public OfferByIdRsDto getOfferById(UUID offerId) {
        OfferEntity offerEntity = offerRepository.findById(offerId).orElse(null);
        if (offerEntity == null) return null;

        return OfferByIdRsDto.builder()
                .offerId(offerEntity.getId().toString())
                .gameId(offerEntity.getGame().getSecondId())
                .secondId(offerEntity.getSecondId())
                .gameName(offerEntity.getGame().getTitle())
                .title(offerEntity.getTitle())
                .description(offerEntity.getDescription())
                .imageUrl(offerEntity.getImageUrl())
                .categories(offerEntity.getCategories())
                .price(offerEntity.getPrice().doubleValue())
                .sections(offerEntity.getSections().stream().map(offerMapper::toOfferSectionRsDto).toList())
                .build();
    }

    @Override
    public void addOfferToCart(OfferToCartRqDto offer) {
        Optional<OfferEntity> offerEntityOptional = offerRepository.findById(offer.getOfferId());

        OfferCartEntity offerCartEntity = OfferCartEntity.builder()
                .offer(offerEntityOptional.orElse(null))
                .creator(authService.getAuthUser())
                .gameName(offer.getGameName())
                .basePrice(offer.getBasePrice())
                .totalPrice(offer.getTotalPrice())
                .totalTime(offer.getTotalTime())
                .optionCarts(toOptionCarts(offer.getSelectedOptions()))
                .build();
        offerCartRepository.save(offerCartEntity);
    }

    private List<OfferOptionCartEntity> toOptionCarts(List<SelectedOptionToCartDto> options) {
        if (options == null || options.isEmpty()) {
            return emptyList();
        }
        return options.stream()
                .map(this::toOfferOptionCartEntity)
                .toList();
    }

    private OfferOptionCartEntity toOfferOptionCartEntity(SelectedOptionToCartDto selectedOptionToCartDto) {
        return OfferOptionCartEntity.builder()
                .optionTitle(selectedOptionToCartDto.getOptionTitle())
                .label(selectedOptionToCartDto.getValue().toString())
                .value(selectedOptionToCartDto.getLabel().toString())
                .build();
    }

}
