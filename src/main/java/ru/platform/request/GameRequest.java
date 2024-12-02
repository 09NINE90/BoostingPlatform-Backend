package ru.platform.request;

import lombok.Data;
import ru.platform.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

@Data
public class GameRequest {
    private UUID id;
    private String title;
    private String description;
    private List<CategoryEntity> categories;
    private int pageNumber;
    private int pageSize;
}
