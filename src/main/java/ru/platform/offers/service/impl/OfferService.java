package ru.platform.offers.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.exception.PlatformException;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dao.PlatformEntity;
import ru.platform.games.dto.response.PlatformDto;
import ru.platform.games.repository.GameRepository;
import ru.platform.games.repository.PlatformRepository;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.offers.PaginationOffersUtil;
import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.offers.dao.OfferOptionCartEntity;
import ru.platform.offers.dao.repository.OfferCartRepository;
import ru.platform.offers.dto.request.OfferFilterRqDto;
import ru.platform.offers.dto.request.AddToCartRequestDto;
import ru.platform.offers.dto.response.OfferDetailsRsDto;
import ru.platform.offers.dto.response.CartItemRsDto;
import ru.platform.offers.dto.response.GameOffersRsDto;
import ru.platform.offers.dto.response.FilteredOffersRsDto;
import ru.platform.offers.mapper.IOfferMapper;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.repository.OfferRepository;
import ru.platform.offers.dao.specification.OfferSpecification;
import ru.platform.offers.service.IOfferService;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;
import static ru.platform.exception.ErrorType.NOT_VALID_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final OfferSpecification specification;
    private final OfferCartRepository offerCartRepository;
    private final PlatformRepository platformRepository;
    private final GameRepository gameRepository;
    private final PaginationOffersUtil paginationOffersUtil;
    private final IAuthService authService;
    private final IOfferMapper offerMapper;

    private final String LOG_PREFIX = "OfferService: {}";

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.OFFERS_BY_GAME_ID)
    public List<GameOffersRsDto> getOffersByGameId(UUID gameId) {
        try {
            List<OfferEntity> offersByGame = offerRepository.findAllByGameId(gameId);
            log.debug(LOG_PREFIX, "Preparing a response for the frontend");
            return offersByGame.stream()
                    .map(offerMapper::toOfferByGameIdRsDto)
                    .toList();
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Error when searching for offers by game ID");
            throw new EntityNotFoundException("Error when searching for offers by game ID", e);
        }
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.OFFERS_WITH_FILTERS)
    public FilteredOffersRsDto getOffersByRequest(OfferFilterRqDto request) {
        return mapToResponse(getServicePageFunc().apply(request));
    }

    private FilteredOffersRsDto mapToResponse(Page<OfferEntity> entities) {
        List<GameOffersRsDto> mappedOffers = entities
                .stream()
                .map(offerMapper::toOfferByGameIdRsDto)
                .toList();

        return FilteredOffersRsDto.builder()
                .offers(mappedOffers)
                .pageSize(entities.getSize())
                .pageTotal(entities.getTotalPages())
                .pageNumber(entities.getNumber() + 1)
                .recordTotal(entities.getTotalElements())
                .build();
    }

    private Function<OfferFilterRqDto, Page<OfferEntity>> getServicePageFunc() {
        try {
            return request -> offerRepository
                    .findAll(specification.getFilter(request), paginationOffersUtil.getPageRequest(request));
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Error when searching for offers with sorting, filters, and pagination");
            throw new PlatformException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public OfferDetailsRsDto getOfferById(UUID offerId) {
        OfferEntity offerEntity = offerRepository.findById(offerId).orElse(null);
        if (offerEntity == null) return null;

        return OfferDetailsRsDto.builder()
                .offerId(offerEntity.getId())
                .gameId(offerEntity.getGame().getId())
                .secondGameId(offerEntity.getGame().getSecondId())
                .secondId(offerEntity.getSecondId())
                .gameName(offerEntity.getGame().getTitle())
                .gamePlatforms(offerEntity.getGame().getPlatforms().stream()
                        .map(this::toPlatformDto)
                        .toList()
                )
                .title(offerEntity.getTitle())
                .description(offerEntity.getDescription())
                .imageUrl(offerEntity.getImageUrl())
                .categories(offerEntity.getCategories())
                .price(offerEntity.getPrice().doubleValue())
                .sections(offerEntity.getSections().stream().map(offerMapper::toOfferSectionRsDto).toList())
                .build();
    }

    private PlatformDto toPlatformDto(PlatformEntity entity) {
        return PlatformDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .name(entity.getName())
                .build();
    }

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.ADD_OFFER_TO_CART)
    public List<CartItemRsDto> addOfferToCart(AddToCartRequestDto offer) {
        Optional<OfferEntity> offerEntityOptional = offerRepository.findById(offer.getOfferId());
        UserEntity user = authService.getAuthUser();
        GameEntity game = gameRepository.findById(UUID.fromString(offer.getGameId()))
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        PlatformEntity platform = platformRepository.findByTitle(offer.getGamePlatform())
                .orElseThrow(() -> new PlatformException(NOT_FOUND_ERROR));

        if (!game.getPlatforms().contains(platform)) {
            throw new PlatformException(NOT_VALID_REQUEST);
        }

        OfferCartEntity offerCartEntity = OfferCartEntity.builder()
                .offer(offerEntityOptional.orElse(null))
                .creator(authService.getAuthUser())
                .game(game)
                .gamePlatform(platform)
                .basePrice(offer.getBasePrice())
                .totalPrice(offer.getTotalPrice())
                .totalTime(offer.getTotalTime())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .optionCarts(toOptionCarts(offer.getSelectedOptions()))
                .build();
        offerCartRepository.save(offerCartEntity);

        return getAllOfferCartByUser(user);
    }

    @Override
    public List<CartItemRsDto> getCartItems() {
        UserEntity user = authService.getAuthUser();
        return getAllOfferCartByUser(user);
    }

    @Override
    public int getCountCartItems() {
        UserEntity user = authService.getAuthUser();
        return (int) offerCartRepository.countByCreator(user);
    }

    /**
     * Удаление элемента из корзины по id
     */
    @Override
    @Transactional
    public void deleteCartItemById(UUID itemId) {
        offerCartRepository.deleteById(itemId);
    }

    /**
     * Получение списка объектов корзины конкретного пользователя
     */
    private List<CartItemRsDto> getAllOfferCartByUser(UserEntity user) {
        List<OfferCartEntity> offerCartEntities = offerCartRepository.findAllByCreator(user);
        return offerCartEntities.stream().map(offerMapper::toOfferCartRsDto).toList();
    }

    private List<OfferOptionCartEntity> toOptionCarts(List<AddToCartRequestDto.SelectedOptionToCartDto> options) {
        if (options == null || options.isEmpty()) return emptyList();

        return options.stream()
                .map(this::toOfferOptionCartEntity)
                .toList();
    }

    private OfferOptionCartEntity toOfferOptionCartEntity(AddToCartRequestDto.SelectedOptionToCartDto selectedOptionToCartDto) {
        return OfferOptionCartEntity.builder()
                .optionTitle(selectedOptionToCartDto.getOptionTitle())
                .label(selectedOptionToCartDto.getValue().toString())
                .value(selectedOptionToCartDto.getLabel().toString())
                .build();
    }

}
