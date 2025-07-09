package ru.platform.orders.sorting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSortFilter {

    @Schema(description = "Ключ сортировки", example = "PRICE", enumAsRef = true)
    private OrderSortKeys key;

    @Schema(description = "Направлние сортировки", example = "true")
    private Boolean asc;
}
