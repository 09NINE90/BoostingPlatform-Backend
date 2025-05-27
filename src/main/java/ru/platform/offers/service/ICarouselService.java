package ru.platform.offers.service;

import ru.platform.offers.dto.response.CarouselRsDto;

import java.util.List;

public interface ICarouselService {
    List<CarouselRsDto> getItems();
}
