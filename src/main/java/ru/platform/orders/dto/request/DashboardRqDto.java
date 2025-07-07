package ru.platform.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.sorting.OrderSortFilter;

import java.util.Set;

@Data
@Builder
public class DashboardRqDto {

    @Schema(description = "Статус заказа", enumAsRef = true, hidden = true)
    private OrderStatus status;

    @Schema(description = "Название игры", example = "Game name")
    private Set<String> gameNames;

    @Schema(description = "Название платформы для игр", example = "PS")
    private Set<String> gamePlatforms;

    @Schema(description = "Цена от/до")
    private PriceDto totalPrice;

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
