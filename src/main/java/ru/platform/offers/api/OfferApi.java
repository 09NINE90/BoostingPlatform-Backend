package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.dto.response.OffersByGameIdRsDto;
import ru.platform.offers.dto.response.OffersListRsDto;
import ru.platform.offers.service.IOfferService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offer")
public class OfferApi {

    private final IOfferService service;

    @GetMapping("/getOffersByGameId/{gameId}")
    @Schema(
            description = "Получение предложений по идентификатору игры"
    )
    public ResponseEntity<OffersListRsDto<OffersByGameIdRsDto>> getOffersByGameId(@PathVariable UUID gameId) {
        OffersListRsDto<OffersByGameIdRsDto> result = service.getOffersByGameId(gameId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getOffersByRequest")
    @Schema(
            description = "Получение предложений с сортировкой, фильтрами и пагинацией"
    )
    public ResponseEntity<OffersListRsDto<OffersByGameIdRsDto>> getOffersByRequest(@RequestBody OfferRqDto request){
        return ResponseEntity.ok(service.getOffersByRequest(request));
    }

}
