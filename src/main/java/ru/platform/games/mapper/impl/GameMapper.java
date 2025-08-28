package ru.platform.games.mapper.impl;

import org.springframework.stereotype.Component;
import ru.platform.games.dao.CategoryEntity;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.CategoryRsDto;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameItemRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.mapper.IGameMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameMapper implements IGameMapper {

    @Override
    public List<GameMainPageRsDto> toGameListRs(List<GameEntity> allGames) {
        List<GameMainPageRsDto> games = new ArrayList<>();
        allGames.forEach(e ->
            games.add(new GameMainPageRsDto(
                    e.getId(),
                    e.getSecondId(),
                    e.getTitle(),
                    e.getMiniImageUrl()))
        );
        return games;
    }

    @Override
    public GameBySecondIdRsDto toGameBySecondId(GameEntity gameEntity) {
        return GameBySecondIdRsDto.builder()
                .id(gameEntity.getId())
                .secondId(gameEntity.getSecondId())
                .name(gameEntity.getTitle())
                .categories(gameEntity.getCategories()
                        .stream()
                        .map(this::toCategoryRsDto)
                        .toList())
                .build();
    }

    @Override
    public GameItemRsDto toGameItemRsDto(GameEntity entity) {
        return GameItemRsDto.builder()
                .id(entity.getId())
                .name(entity.getTitle())
                .build();
    }

    private CategoryRsDto toCategoryRsDto(CategoryEntity categoryEntity) {
        return CategoryRsDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

}
