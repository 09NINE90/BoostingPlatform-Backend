package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.offers.dto.response.OfferOptionRsDto;
import ru.platform.offers.service.IOfferOptionService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequestMapping("/api/offer/option")
@RequiredArgsConstructor
@Tag(name = OFFER_OPTION_TAG_NAME, description = OFFER_OPTION_TAG_DESCRIPTION)
public class OfferOptionApi {

    private final IOfferOptionService offerOptionService;

    @GetMapping("/byOfferId/{offerId}")
    @Operation(summary = "Получение опций по предложению")
    public List<OfferOptionRsDto>  getOptionsByOfferId(@PathVariable("offerId") String offerId) {
        return offerOptionService.getOptionsByOfferId(UUID.fromString(offerId));
    }
}
