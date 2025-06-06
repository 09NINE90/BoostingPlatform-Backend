package ru.platform.games.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.games.dao.CategoryEntity;
import ru.platform.games.dto.response.CategoryRsDto;
import ru.platform.games.repository.CategoryRepository;
import ru.platform.games.service.ICategoryService;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.CATEGORIES_BY_GAME_ID)
    public List<CategoryRsDto> getCategoriesByGameId(String gameId) {
        return repository.findAllByGameId(UUID.fromString(gameId))
                .stream()
                .flatMap(this::flattenCategoryWithChildren)
                .map(this::toCategoryRsDto)
                .toList();
    }

    private CategoryRsDto toCategoryRsDto(CategoryEntity category) {
        return CategoryRsDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .build();
    }

    private Stream<CategoryEntity> flattenCategoryWithChildren(CategoryEntity category) {
        return Stream.concat(
                Stream.of(category),
                category.getSubcategories().stream()
                        .flatMap(this::flattenCategoryWithChildren)
        );
    }

}
