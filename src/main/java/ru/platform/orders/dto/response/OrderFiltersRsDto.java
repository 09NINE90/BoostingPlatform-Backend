package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.games.enumz.GamePlatform;
import ru.platform.orders.enumz.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderFiltersRsDto {

    @ArraySchema(
            arraySchema = @Schema(description = "Доступные статусы заказов"),
            schema = @Schema(
                    description = "Статус заказа",
                    implementation = OrderStatus.class,
                    enumAsRef = true
            )
    )
    private List<OrderStatus> statuses;

    @ArraySchema(
            arraySchema = @Schema(description = "Доступные игры"),
            schema = @Schema(description = "Название игры", example = "Destiny 2")
    )
    private List<String> gameNames;

    @ArraySchema(
            arraySchema = @Schema(description = "Доступные платформы"),
            schema = @Schema(description = "Платформа", example = "PS")
    )
    private List<GamePlatform> gamePlatforms;

    @Schema(description = "Цена min/max для фильтров")
    private PriceFilterDto price;


    @Data
    @Builder
    @Schema(description = "Диапазон цен")
    public static class PriceFilterDto {

        @Schema(description = "Минимальная стоимость заказа для фильтра", example = "100")
        private BigDecimal priceMin;

        @Schema(description = "Максимальная стоимость заказа для фильтра", example = "200")
        private BigDecimal priceMax;
    }

}
