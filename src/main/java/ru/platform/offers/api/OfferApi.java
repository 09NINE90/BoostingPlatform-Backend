package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.offers.dto.request.OfferToCartRqDto;
import ru.platform.offers.dto.response.OfferCartRsDto;
import ru.platform.offers.service.IOfferService;

import java.util.List;

import static ru.platform.LocalConstants.Api.OFFER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.OFFER_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offer")
@Tag(name = OFFER_TAG_NAME, description = OFFER_TAG_DESCRIPTION)
public class OfferApi {

    private final IOfferService service;

    @PostMapping("/addToCart")
    @Operation(summary = "Добавление предложенияв корзину")
    public ResponseEntity<List<OfferCartRsDto>> addOfferToCart(@RequestBody OfferToCartRqDto request) {
        return ResponseEntity.ok(service.addOfferToCart(request));
    }

    @GetMapping("/getCartItems")
    @Operation(summary = "Получение содержания корзины пользователя")
    public ResponseEntity<List<OfferCartRsDto>> getCartItems() {
        return ResponseEntity.ok(service.getCartItems());
    }

    @GetMapping("/getCountCartItems")
    @Operation(summary = "Получение содержания корзины пользователя")
    public ResponseEntity<Integer> getCountCartItems() {
        return ResponseEntity.ok(service.getCountCartItems());
    }

}
