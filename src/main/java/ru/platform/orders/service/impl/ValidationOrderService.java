package ru.platform.orders.service.impl;

import org.springframework.stereotype.Service;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.orders.dto.request.CartItemDto;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.service.IValidationOrderService;
import ru.platform.utils.DtoUtil;

@Service
public class ValidationOrderService implements IValidationOrderService {

    @Override
    public void validateCreateOrderRqDto(CreateOrderRqDto orderRqDto) {
        if (DtoUtil.isDeepEmpty(orderRqDto, CreateOrderRqDto::getItems)) {
            throw new PlatformException(ErrorType.NOT_VALID_REQUEST);
        }
        for (CartItemDto item : orderRqDto.getItems()) {
            validateCartItemDto(item);
        }
    }

    private void validateCartItemDto(CartItemDto item) {
        if (DtoUtil.isDeepEmpty(item, CartItemDto::getOfferId) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getOfferName) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getGameName) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getBasePrice) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getTotalPrice) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getTotalTime) ||
                DtoUtil.isDeepEmpty(item, CartItemDto::getGamePlatform)) {
            throw new PlatformException(ErrorType.NOT_VALID_REQUEST);
        }
    }
}
