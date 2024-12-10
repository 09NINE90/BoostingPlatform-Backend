package ru.platform.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.platform.entity.enums.ESortKeys;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SortFilter {
    private ESortKeys key;
    private Boolean asc;
}
