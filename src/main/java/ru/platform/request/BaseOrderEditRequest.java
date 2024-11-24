package ru.platform.request;

import lombok.Data;
import ru.platform.entity.GameEntity;

@Data
public class BaseOrderEditRequest {
    private String title;
    private String description;
    private float basePrice;
    private GameEntity game;

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
