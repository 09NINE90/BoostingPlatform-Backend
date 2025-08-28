package ru.platform.offers.service;

import ru.platform.offers.dto.request.OfferFilterRqDto;
import ru.platform.offers.dto.request.AddToCartRequestDto;
import ru.platform.offers.dto.response.OfferDetailsRsDto;
import ru.platform.offers.dto.response.CartItemRsDto;
import ru.platform.offers.dto.response.GameOffersRsDto;
import ru.platform.offers.dto.response.FilteredOffersRsDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с игровыми предложениями и корзиной пользователя.
 */
public interface IOfferService {

    /**
     * Возвращает отфильтрованный список предложений по заданному запросу.
     */
    FilteredOffersRsDto getOffersByRequest(OfferFilterRqDto request);

    /**
     * Возвращает список предложений, связанных с определённой игрой.
     */
    List<GameOffersRsDto> getOffersByGameId(UUID gameId);

    /**
     * Возвращает подробную информацию о предложении по его идентификатору.
     */
    OfferDetailsRsDto getOfferById(UUID offerId);

    /**
     * Добавляет предложение в корзину пользователя и возвращает обновлённый список элементов корзины.
     */
    List<CartItemRsDto> addOfferToCart(AddToCartRequestDto offer);

    /**
     * Возвращает список всех элементов, добавленных в корзину.
     */
    List<CartItemRsDto> getCartItems();

    /**
     * Возвращает количество элементов в корзине.
     */
    int getCountCartItems();

    /**
     * Удаляет элемент из корзины по его идентификатору.
     */
    void deleteCartItemById(UUID itemId);
}

