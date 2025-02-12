package ru.platform.request;

import lombok.Data;
import ru.platform.entity.GameEntity;
import ru.platform.inner.SortFilter;

@Data
public class OrderServicesRequest {
    private String imageUrl;
    private String title;
    private String description;
    private float price;
    private GameEntity game;
    private int pageNumber;
    private int pageSize;
    private String categories;
    private SortFilter sort;
    private String gameId;
}