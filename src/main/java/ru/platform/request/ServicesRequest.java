package ru.platform.request;

import lombok.Data;
import ru.platform.entity.GameEntity;
import ru.platform.inner.SortFilter;

@Data
public class ServicesRequest {
    private String imageUrl;
    private String title;
    private String description;
    private float basePrice;
    private GameEntity game;
    private int pageNumber;
    private int pageSize;
    private String categories;
    private SortFilter sort;

    @Override
    public String toString() {
        return "BaseOrderEditRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", game=" + game +
                '}';
    }
}
