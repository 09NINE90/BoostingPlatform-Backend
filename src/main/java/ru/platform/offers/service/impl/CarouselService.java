package ru.platform.offers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.offers.dao.CarouselEntity;
import ru.platform.offers.dao.repository.CarouselRepository;
import ru.platform.offers.dto.response.CarouselRsDto;
import ru.platform.offers.service.ICarouselService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarouselService implements ICarouselService {

    private final CarouselRepository carouselRepository;

    @Override
    public List<CarouselRsDto> getItems() {
        List<CarouselEntity> entity = carouselRepository.findAllByIsActive(true);
        return entity.stream()
                .map(e -> CarouselRsDto.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .description(e.getDescription())
                        .imageUrl(e.getImageUrl())
                        .build())
                .toList();
    }
}
