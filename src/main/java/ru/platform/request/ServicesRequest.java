package ru.platform.request;

import lombok.Data;
import ru.platform.entity.GameEntity;
import ru.platform.entity.OptionsEntity;
import ru.platform.inner.SortFilter;

import java.util.List;

@Data
public class ServicesRequest {
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
    List<OptionsEntity> options;
}