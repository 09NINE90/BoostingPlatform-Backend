package ru.platform.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.platform.LocalConstants;
import ru.platform.entity.CategoryEntity;
import ru.platform.entity.GameEntity;
import ru.platform.repository.GameRepository;
import ru.platform.request.CategoryRequest;
import ru.platform.request.GameRequest;
import ru.platform.response.GameResponse;
import ru.platform.service.IGameService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final GameRepository repository;

    @Override
    public List<GameEntity> getAllGames() {
        return repository.findAll();
    }

    @Override
    public GameResponse getAllGamesByPage(GameRequest request) {
        return mapToResponse(getBaseOrderPageFunc().apply(request));
    }

    @Override
    @Transactional
    public void addNewGame(GameRequest request) {
        GameEntity gameEntity = GameEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categories(new ArrayList<>()) // Пустой список для избежания ошибок
                .build();

        repository.save(gameEntity);

        List<CategoryEntity> categoryEntities = processCategoryHierarchy(request.getCategories(), null);
        gameEntity.setCategories(categoryEntities);

        repository.save(gameEntity);
    }

    @Override
    @Transactional
    public GameEntity updateGame(GameRequest request) {
        GameEntity existingGame = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Game with ID " + request.getId() + " not found"));

        existingGame.setTitle(request.getTitle());
        existingGame.setDescription(request.getDescription());

        if (existingGame.getCategories() == null) {
            existingGame.setCategories(new ArrayList<>());
        }
        updateCategoryHierarchy(existingGame.getCategories(), request.getCategories(), existingGame);

        repository.save(existingGame);
        return repository.save(existingGame);
    }

    private void updateCategoryHierarchy(List<CategoryEntity> existingCategories,
                                         List<CategoryRequest> newCategories,
                                         GameEntity game) {
        existingCategories.clear();

        if (newCategories != null && !newCategories.isEmpty()) {
            for (CategoryRequest categoryRequest : newCategories) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setName(categoryRequest.getName());
                categoryEntity.setGame(game);

                updateSubcategories(categoryEntity, categoryRequest.getSubcategories());

                existingCategories.add(categoryEntity);
            }
        }
    }

    private void updateSubcategories(CategoryEntity parentCategory, List<CategoryRequest> subcategories) {
        if (parentCategory.getSubcategories() == null) {
            parentCategory.setSubcategories(new ArrayList<>());
        }

        parentCategory.getSubcategories().clear();

        if (subcategories != null && !subcategories.isEmpty()) {
            for (CategoryRequest subcategoryRequest : subcategories) {
                CategoryEntity subcategoryEntity = new CategoryEntity();
                subcategoryEntity.setName(subcategoryRequest.getName());
                subcategoryEntity.setParent(parentCategory);

                updateSubcategories(subcategoryEntity, subcategoryRequest.getSubcategories());

                parentCategory.getSubcategories().add(subcategoryEntity);
            }
        }
    }

    private GameEntity mapGamesFrom(GameEntity e){
        return GameEntity.builder()
                .id(e.getId())
                .title(e.getTitle())
                .categories(e.getCategories())
                .description(e.getDescription())
                .build();
    }

    private GameResponse mapToResponse(Page<GameEntity> entities){
        List<GameEntity> mappedOrders = entities.stream().map(this::mapGamesFrom).collect(Collectors.toList());
        return GameResponse.builder()
                .games(mappedOrders)
                .pageNumber(entities.getNumber() + 1)
                .pageSize(entities.getSize())
                .pageTotal(entities.getTotalPages())
                .recordTotal(entities.getTotalElements())
                .build();
    }

    private Function<GameRequest, Page<GameEntity>> getBaseOrderPageFunc(){
        return request -> repository.findAll(getPageRequest(request));
    }

    private PageRequest getPageRequest(GameRequest request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request));
    }

    private int getPageBy(GameRequest request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(GameRequest request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <=0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }

    private List<CategoryEntity> processCategoryHierarchy(List<CategoryRequest> categoryRequests, CategoryEntity parent) {
        if (categoryRequests == null || categoryRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<CategoryEntity> categoryEntities = new ArrayList<>();

        for (CategoryRequest categoryRequest : categoryRequests) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(categoryRequest.getName());
            categoryEntity.setParent(parent);

            List<CategoryEntity> subcategoryEntities = processCategoryHierarchy(categoryRequest.getSubcategories(), categoryEntity);
            categoryEntity.setSubcategories(subcategoryEntities);

            categoryEntities.add(categoryEntity);
        }

        return categoryEntities;
    }
    public GameResponse getGameWithCategories(String gameId) {
        GameEntity game = repository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new RuntimeException("Game not found"));

        List<GameResponse.CategoryDTO> categories = game.getCategories()
                .stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());

        // Создаем DTO
        return GameResponse.builder()
                .id(game.getId().toString())
                .title(game.getTitle())
                .description(game.getDescription())
                .categories(categories)
                .build();
    }

    private GameResponse.CategoryDTO toCategoryDTO(CategoryEntity category) {
        return new GameResponse.CategoryDTO(
                category.getName(),
                category.getSubcategories().stream()
                        .map(this::toCategoryDTO)
                        .collect(Collectors.toList())
        );
    }

}
