package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.sorting.OrderSortFilter;
import ru.platform.user.dao.UserEntity;

@Data
@Builder
public class OrdersByFiltersRqDto {

    @Schema(hidden = true)
    private UserEntity creator;

    @Schema(description = "Статус заказа", enumAsRef = true)
    private OrderStatus status;

    @Schema(description = "Название игры", example = "Game name")
    private String gameName;

    @Schema(description = "Название платформы для игр", example = "PS")
    private String gamePlatform;

    @Schema(description = "Цена от/до")
    private PriceDto price;

    @Schema(description = "Сортировка", example = "PRICE")
    private OrderSortFilter sort;

    @Schema(description = "Номер страницы для получения данных", example = "1")
    private int pageNumber;

    @Schema(description = "Количество объектов на одной странице", example = "20")
    private int pageSize;


    @Data
    @Builder
    public static class PriceDto {

        @Schema(description = "Начальная стоимость заказа для фильтра", example = "100")
        private Double priceFrom;

        @Schema(description = "Конечная стоимость заказа для фильтра", example = "200")
        private Double priceTo;
    }
}
