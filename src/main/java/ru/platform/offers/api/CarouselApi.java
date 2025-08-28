package ru.platform.offers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.offers.dto.response.CarouselRsDto;
import ru.platform.offers.service.ICarouselService;

import java.util.List;

import static ru.platform.LocalConstants.Api.CAROUSEL_TAG_NAME;
import static ru.platform.LocalConstants.Api.CAROUSEL_TAG_DESCRIPTION;

@RestController
@RequestMapping("/api/carousel")
@RequiredArgsConstructor
@Tag(name = CAROUSEL_TAG_NAME, description = CAROUSEL_TAG_DESCRIPTION)
public class CarouselApi {

    private final ICarouselService carouselService;

    @GetMapping
    @Operation(summary = "Получение объектов карусели")
    public ResponseEntity<List<CarouselRsDto>> getItems() {
        List<CarouselRsDto> result = carouselService.getItems();
        return ResponseEntity.ok(result);
    }
}
