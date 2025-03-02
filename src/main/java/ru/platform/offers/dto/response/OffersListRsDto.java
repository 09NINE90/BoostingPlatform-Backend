package ru.platform.offers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OffersListRsDto<T> {

    List<T> offers;
    private int pageTotal;
    private int pageNumber;
    private int pageSize;
    private long recordTotal;

    public OffersListRsDto(List<T> offers) {
        this.offers = offers;
    }
}
