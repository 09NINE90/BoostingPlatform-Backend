package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.sorting.OrderSortFilter;
import ru.platform.user.dao.UserEntity;

@Data
@Builder
public class OrdersByBoosterRqDto {

    @Schema(hidden = true)
    private UserEntity worker;

    @Schema(description = "Статус заказа", enumAsRef = true)
    private OrderStatus status;

    @Schema(description = "Название игры", example = "Game name")
    private String gameName;

    @Schema(description = "Название платформы для игр", example = "PS")
    private String gamePlatform;

    @Schema(description = "Цена от/до")
    private OrdersByFiltersRqDto.PriceDto price;

    @Schema(description = "Сортировка", example = "PRICE")
    private OrderSortFilter sort;

    @Data
    @Builder
    public static class PriceDto {

        @Schema(description = "Начальная стоимость заказа для фильтра", example = "100")
        private Double priceFrom;

        @Schema(description = "Конечная стоимость заказа для фильтра", example = "200")
        private Double priceTo;
    }
}
