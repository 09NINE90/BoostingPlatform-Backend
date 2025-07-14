package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Schema(description = "Фильтры для дашборда заказов")
public class DashboardFiltersRsDto {

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Доступные для фильтрации названия игр",
                    example = "[\"Destiny 2\", \"World of Warcraft\"]"
            ),
            schema = @Schema(
                    description = "Название игры",
                    example = "Destiny 2",
                    minLength = 1,
                    maxLength = 100
            )
    )
    private Set<String> gameNames;

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Доступные игровые платформы для фильтрации",
                    example = "[\"PC\", \"PS\", \"XBOX\"]"
            ),
            schema = @Schema(
                    description = "Код платформы",
                    example = "PS5",
                    minLength = 1,
                    maxLength = 10
            )
    )
    private List<String> gamePlatforms;

    @Schema(description = "Цена min/max для фильтров")
    private PriceFilterDto price;


    @Data
    @Builder
    @Schema(description = "Диапазон цен")
    public static class PriceFilterDto {

        @Schema(description = "Минимальная стоимость заказа для фильтра", example = "100")
        private Double priceMin;

        @Schema(description = "Максимальная стоимость заказа для фильтра", example = "200")
        private Double priceMax;
    }
}
