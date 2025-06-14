package ru.platform.orders;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.platform.LocalConstants;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.sorting.OrderSortFilter;
import ru.platform.orders.sorting.OrderSortKeys;

@Component
public class PaginationOrdersUtil {
    
    public PageRequest getPageRequest(OrdersByFiltersRqDto request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request), getSortBy(request));
    }

    private Sort getSortBy(OrdersByFiltersRqDto request) {
        return getSortBy(request.getSort());
    }

    private Sort getSortBy(OrderSortFilter sort) {
        if (sort == null || sort.getKey() == null) {
            sort = new OrderSortFilter(OrderSortKeys.CREATION_AT, false);
        }
        boolean isAsc = sort.getAsc();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sort.getKey().getTitle());
    }

    private int getPageBy(OrdersByFiltersRqDto request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(OrdersByFiltersRqDto request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }
}
