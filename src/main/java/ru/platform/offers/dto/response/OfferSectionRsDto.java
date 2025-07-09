package ru.platform.offers.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.platform.offers.enumz.SectionType;

import java.util.List;

@Data
@Builder
public class OfferSectionRsDto {

    @Schema(description = "Название секции", example = "What you will get")
    private String title;

    @Schema(description = "Тип секции", example = "ACCORDION", enumAsRef = true)
    private SectionType type;

    @Schema(description = "Описание секции", example = "Very long description")
    private String description;

    @ArraySchema(schema = @Schema(description = "Объекты, связанные с секцией"))
    private List<OfferSectionItemRsDto> items;
}
