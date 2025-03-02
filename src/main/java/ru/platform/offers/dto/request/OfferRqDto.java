package ru.platform.offers.dto.request;

import lombok.Data;
import ru.platform.offers.sorting.SortFilter;

@Data
public class OfferRqDto {

    private String gameId;
    private String category;
    private SortFilter sort;
    private int pageNumber;
    private int pageSize;
}
