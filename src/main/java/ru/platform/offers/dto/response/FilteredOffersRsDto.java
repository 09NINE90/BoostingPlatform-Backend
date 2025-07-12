package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Пагинированный список предложений")
public class FilteredOffersRsDto {

    @ArraySchema(
            arraySchema = @Schema(
                    description = "Отфильтрованные предложения",
                    example = """
                [{
                  "id": "550e8400-e29b-41d4-a716-446655440000",
                  "title": "Rank boosting",
                  "description": "Профессиональный буст вашего ранга",
                  "imageUrl": "https://example.com/image.jpg",
                  "price": 100.00
                }]"""
            ),
            schema = @Schema(implementation = GameOffersRsDto.class)
    )
    List<GameOffersRsDto> offers;

    @Schema(description = "Всего страниц", example = "5")
    private int pageTotal;

    @Schema(description = "Номер страницы", example = "1")
    private int pageNumber;

    @Schema(description = "Количество объектов на одной странице", example = "20")
    private int pageSize;

    @Schema(description = "Всего объектов найдено", example = "100")
    private long recordTotal;

    public FilteredOffersRsDto(List<GameOffersRsDto> offers) {
        this.offers = offers;
    }
}
