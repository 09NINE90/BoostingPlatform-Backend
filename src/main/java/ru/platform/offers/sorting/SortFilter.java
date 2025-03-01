package ru.platform.offers.sorting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortFilter {
    private OfferSortKeys key;
    private Boolean asc;
}
