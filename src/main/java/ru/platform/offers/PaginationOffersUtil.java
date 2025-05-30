package ru.platform.offers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.platform.LocalConstants;
import ru.platform.offers.dto.request.OfferRqDto;
import ru.platform.offers.sorting.OfferSortKeys;
import ru.platform.offers.sorting.SortFilter;

@Component
public class PaginationOffersUtil {

    public PageRequest getPageRequest(OfferRqDto request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request), getSortBy(request));
    }

    private Sort getSortBy(OfferRqDto request) {
        return getSortBy(request.getSort());
    }

    private Sort getSortBy(SortFilter sort) {
        if (sort == null || sort.getKey() == null) {
            sort = new SortFilter(OfferSortKeys.CREATED_AT, false);
        }
        boolean isAsc = sort.getAsc();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sort.getKey().getName());
    }

    private int getPageBy(OfferRqDto request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(OfferRqDto request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }
}
