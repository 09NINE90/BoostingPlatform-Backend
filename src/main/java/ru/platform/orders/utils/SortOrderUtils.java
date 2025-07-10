package ru.platform.orders.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import ru.platform.orders.sorting.OrderSortFilter;
import ru.platform.orders.sorting.OrderSortKeys;

@UtilityClass
public class SortOrderUtils {

    public static Sort getSortByWithDefaultCreationAt(OrderSortFilter sort) {
        if (sort == null || sort.getKey() == null) {
            sort = new OrderSortFilter(OrderSortKeys.CREATION_AT, false);
        }
        boolean isAsc = sort.getAsc();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sort.getKey().getTitle());
    }

    public static Sort getSortByWithDefaultStartTimeExecution(OrderSortFilter sort) {
        if (sort == null || sort.getKey() == null) {
            sort = new OrderSortFilter(OrderSortKeys.START_TIME_EXECUTION, false);
        }
        boolean isAsc = sort.getAsc();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sort.getKey().getTitle());
    }
}
