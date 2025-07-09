package ru.platform.offers.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.platform.offers.sorting.SortFilter;

import static ru.platform.LocalConstants.Variables.DEFAULT_UUID;

@Data
public class OfferFilterRqDto {

    @Schema(description = "Идентификатор игры, к которой относится предложение", example = DEFAULT_UUID)
    private String gameId;

    @Schema(description = "Название категорий игры, к которым относится предложение", example = "PVP, PVE")
    private String category;

    @Schema(description = "Поле для указания сортировки")
    private SortFilter sort;

    @Schema(description = "Номер страницы для получения данных", example = "1")
    private int pageNumber;

    @Schema(description = "Количество объектов на одной странице", example = "20")
    private int pageSize;
}
