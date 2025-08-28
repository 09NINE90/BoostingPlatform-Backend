package ru.platform.offers.service;

import ru.platform.offers.dto.response.CarouselRsDto;

import java.util.List;

/**
 * Сервис для получения элементов карусели на главной странице.
 */
public interface ICarouselService {

    /**
     * Возвращает список элементов карусели.
     */
    List<CarouselRsDto> getItems();
}

