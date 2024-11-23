package ru.platform.request;

import lombok.Builder;
import lombok.Data;
import ru.platform.entity.BaseOrdersEntity;

import java.util.List;

@Data
@Builder
public class BaseOrderRequest {

    private List<BaseOrdersEntity> baseOrder;
    private int pageNumber;
    private int pageSize;
}
