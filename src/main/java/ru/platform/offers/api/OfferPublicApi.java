package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.dto.response.OfferByIdRsDto;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.offers.dto.response.OffersListRsDto;
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
    @Operation(summary = "Получение предложений по идентификатору игры")
    public ResponseEntity<List<OffersByGameIdRsDto>> getOffersByGameId(@PathVariable("gameId") UUID gameId) {
        List<OffersByGameIdRsDto> result = service.getOffersByGameId(gameId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getOffersByRequest")
    @Operation(summary = "Получение предложений с сортировкой, фильтрами и пагинацией")
    public ResponseEntity<OffersListRsDto> getOffersByRequest(@RequestBody OfferRqDto request){
        return ResponseEntity.ok(service.getOffersByRequest(request));
    }

    @GetMapping("/{offerId}")
    @Operation(summary = "Получение предложения по ID")
    public ResponseEntity<OfferByIdRsDto> findOfferById(@PathVariable UUID offerId) {
        return ResponseEntity.ok(service.getOfferById(offerId));
    }

    @GetMapping("/option/byOfferId/{offerId}")
    @Operation(summary = "Получение опций по предложению")
    public List<OfferOptionRsDto>  getOptionsByOfferId(@PathVariable("offerId") String offerId) {
        return offerOptionService.getOptionsByOfferId(UUID.fromString(offerId));
    }
}
