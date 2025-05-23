package ru.platform.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.platform.entity.OrderServicesEntity;

import java.util.List;

@Data
@Builder
public class OrderServicesResponse {

    @JsonProperty("services")
    private List<OrderServicesEntity> services;
    @JsonProperty("pageNumber")
    private int pageNumber;
    @JsonProperty("pageSize")
    private int pageSize;
    @JsonProperty("pageTotal")
    private int pageTotal;
    @JsonProperty("recordTotal")
    private long recordTotal;
    @JsonProperty("categories")
    private long categories;
    @Override
    public String toString() {
        return "BaseOrderResponse{" +
                "baseOrder=" + services +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", recordTotal=" + recordTotal +
                '}';
    }

}
