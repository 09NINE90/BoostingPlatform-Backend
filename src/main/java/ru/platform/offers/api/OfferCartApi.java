package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.offers.dto.request.AddToCartRequestDto;
import ru.platform.offers.dto.response.CartItemRsDto;
import ru.platform.offers.service.IOfferService;

import java.util.List;
import java.util.UUID;

import static ru.platform.LocalConstants.Api.OFFER_CART_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.OFFER_CART_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = OFFER_CART_TAG_NAME, description = OFFER_CART_TAG_DESCRIPTION)
public class OfferCartApi {

    private final IOfferService service;

    @PostMapping("/items")
    @Operation(summary = "Добавить предложение в корзину")
    public ResponseEntity<List<CartItemRsDto>> addOfferToCart(@RequestBody AddToCartRequestDto request) {
        return ResponseEntity.ok(service.addOfferToCart(request));
    }

    @GetMapping("/items")
    @Operation(summary = "Получить содержимое корзины")
    public ResponseEntity<List<CartItemRsDto>> getCartItems() {
        return ResponseEntity.ok(service.getCartItems());
    }

    @GetMapping("/items/count")
    @Operation(summary = "Получить количество элементов в корзине")
    public ResponseEntity<Integer> getCountCartItems() {
        return ResponseEntity.ok(service.getCountCartItems());
    }

    @PostMapping("/items/{itemId}")
    @Operation(summary = "Удалить предложение из корзины")
    public ResponseEntity<Void> removeFromCart(@PathVariable UUID itemId) {
        service.deleteCartItemById(itemId);
        return ResponseEntity.ok().build();
    }
}
