package ru.platform.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.entity.BaseOrdersEntity;

import java.util.List;

@Data
@Builder
public class BaseOrderResponse {

    private List<BaseOrdersEntity> baseOrder;
    private int pageNumber;
    private int pageSize;
    private int pageTotal;
    private long recordTotal;

    @Override
    public String toString() {
        return "BaseOrderResponse{" +
                "baseOrder=" + baseOrder +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", recordTotal=" + recordTotal +
                '}';
    }

}
