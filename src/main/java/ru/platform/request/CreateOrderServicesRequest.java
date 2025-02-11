package ru.platform.request;

import lombok.Builder;
import lombok.Data;
import ru.platform.entity.options_entity.OptionEntity;

import java.util.List;

@Data
@Builder
public class CreateOrderServicesRequest {

    private String title;
    private String description;
    private String imageUrl;
    private String categories;
    private String gameId;
    private float price;
    private List<OptionEntity> options;
}
