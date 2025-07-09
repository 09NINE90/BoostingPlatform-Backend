package ru.platform.offers.sorting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortFilter {

    @Schema(description = "Ключ сортировки", example = "PRICE", enumAsRef = true)
    private OfferSortKeys key;

    @Schema(description = "Направлние сортировки", example = "true")
    private Boolean asc;
}
