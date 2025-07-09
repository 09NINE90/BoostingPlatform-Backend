package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.offers.dto.request.OfferFilterRqDto;
import ru.platform.offers.dto.response.OfferDetailsRsDto;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.GameOffersRsDto;
import ru.platform.offers.dto.response.FilteredOffersRsDto;
import ru.platform.offers.service.IOfferOptionService;
import ru.platform.offers.service.IOfferService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.OFFER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.OFFER_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offer/public")
@Tag(name = OFFER_TAG_NAME, description = OFFER_TAG_DESCRIPTION)
public class OfferPublicApi {

    private final IOfferService service;
    private final IOfferOptionService offerOptionService;

    @GetMapping("/getOffersByGameId/{gameId}")
    @Operation(summary = "Получить предложения по ID игры")
    public ResponseEntity<List<GameOffersRsDto>> getOffersByGameId(@PathVariable("gameId") UUID gameId) {
        List<GameOffersRsDto> result = service.getOffersByGameId(gameId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getOffersByRequest")
    @Operation(summary = "Найти предложения с фильтрами")
    public ResponseEntity<FilteredOffersRsDto> getOffersByRequest(@RequestBody OfferFilterRqDto request) {
        return ResponseEntity.ok(service.getOffersByRequest(request));
    }

    @GetMapping("/{offerId}")
    @Operation(summary = "Получить предложение по ID")
    public ResponseEntity<OfferDetailsRsDto> findOfferById(@PathVariable UUID offerId) {
        return ResponseEntity.ok(service.getOfferById(offerId));
    }

    @GetMapping("/option/byOfferId/{offerId}")
    @Operation(summary = "Получить доступные опции предложения")
    public List<OfferOptionRsDto> getOptionsByOfferId(@PathVariable("offerId") String offerId) {
        return offerOptionService.getOptionsByOfferId(UUID.fromString(offerId));
    }
}
