package ru.platform.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.entity.GameEntity;
import ru.platform.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BaseOrderResponse {

    private List<BaseOrdersEntity> baseOrder;
    private int pageNumber;
    private int pageSize;
    private int pageTotal;
    private int recordTotal;

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
