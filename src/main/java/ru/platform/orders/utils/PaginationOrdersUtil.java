package ru.platform.orders.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import ru.platform.LocalConstants;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;

@UtilityClass
public class PaginationOrdersUtil {
    
    public static PageRequest getPageRequest(OrdersByFiltersRqDto request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request), SortOrderUtils.getSortBy(request));
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
