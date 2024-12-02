package ru.platform.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GameResponse {
    private String id;
    private String title;
    private String description;
    private List<CategoryDTO> categories;

    @Data
    @AllArgsConstructor
    public static class CategoryDTO {
        private String name;
        private List<CategoryDTO> subcategories; // Для вложенных категорий
    }
}
