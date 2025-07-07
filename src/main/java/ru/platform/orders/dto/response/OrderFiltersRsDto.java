package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderFiltersRsDto {

    @ArraySchema(schema = @Schema(description = "Статусы заказов для фильтров", enumAsRef = true))
    private List<String> statuses;

    @ArraySchema(schema =  @Schema(description = "Названия игр для фильтров", example = "Game name"))
    private List<String> gameNames;

    @ArraySchema(schema =  @Schema(description = "Названия игровых платформ для фильтров", example = "PS"))
    private List<String> gamePlatforms;

    @Schema(description = "Цена min/max для фильтров")
    private PriceFilterDto price;


    @Data
    @Builder
    public static class PriceFilterDto {

        @Schema(description = "Минимальная стоимость заказа для фильтра", example = "100")
        private Double priceMin;

        @Schema(description = "Максимальная стоимость заказа для фильтра", example = "200")
        private Double priceMax;
    }

}
