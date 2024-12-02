package ru.platform.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.platform.LocalConstants;
import ru.platform.entity.CategoryEntity;
import ru.platform.entity.GameEntity;
import ru.platform.repository.CategoryRepository;
import ru.platform.repository.GameRepository;
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
    private final CategoryRepository repositoryCategories;

    @Override
    public List<GameEntity> getAllGames() {
        return repository.findAll();
    }

    @Override
    public GameResponse getAllGamesByPage(GameRequest request) {
        return mapToResponse(getBaseOrderPageFunc().apply(request));
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

    @Override
    @Transactional
    public void addNewGame(GameRequest request) {
        GameEntity gameEntity = GameEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categories(new ArrayList<>()) // Пустой список для избежания ошибок
                .build();

        repository.save(gameEntity);

        request.getCategories().forEach(category -> processCategoryHierarchy(category, gameEntity));
        gameEntity.setCategories(request.getCategories());

        repository.save(gameEntity);
    }


    private void processCategoryHierarchy(CategoryEntity category, GameEntity game) {
        category.setGame(game);

        if (category.getSubcategories() != null && !category.getSubcategories().isEmpty()) {
            category.getSubcategories().forEach(subcategory -> {
                subcategory.setParent(category);
                processCategoryHierarchy(subcategory, game);
            });
        }
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
