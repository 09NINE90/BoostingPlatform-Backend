package ru.platform.request;

import lombok.Data;
import ru.platform.entity.GameEntity;

import java.util.List;

@Data
public class CategoryRequest {
    private Long id;
    private String name;
    private GameEntity game;
    private CategoryRequest parent;
    private List<CategoryRequest> subcategories;
}
