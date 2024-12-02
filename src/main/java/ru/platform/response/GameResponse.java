package ru.platform.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.platform.entity.GameEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GameResponse {
    private String id;
    private String title;
    private String description;
    private List<CategoryDTO> categories;
    private List<GameEntity> games;
    @JsonProperty("pageNumber")
    private int pageNumber;
    @JsonProperty("pageSize")
    private int pageSize;
    @JsonProperty("pageTotal")
    private int pageTotal;
    @JsonProperty("recordTotal")
    private long recordTotal;

    @Data
    @AllArgsConstructor
    public static class CategoryDTO {
        private String name;
        private List<CategoryDTO> subcategories; // Для вложенных категорий
    }
}
