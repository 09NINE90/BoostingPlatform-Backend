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
public class FilteredOffersRsDto {

    @ArraySchema(schema = @Schema(description = "Список предложений"))
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
