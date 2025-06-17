package ru.platform.orders.service;

import ru.platform.orders.dto.request.CreateOrderRqDto;

public interface IValidationOrderService {
    void validateCreateOrderRqDto(CreateOrderRqDto orderRqDto);
}
