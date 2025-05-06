package ru.platform.games.mapper.impl;

import org.springframework.stereotype.Component;
import ru.platform.games.dao.CategoryEntity;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.CategoryRsDto;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
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
                    e.getId().toString(),
                    e.getSecondId(),
                    e.getTitle()))
        );
        return games;
    }

    @Override
    public GameBySecondIdRsDto toGameBySecondId(GameEntity gameEntity) {
        return GameBySecondIdRsDto.builder()
                .id(gameEntity.getId().toString())
                .secondId(gameEntity.getSecondId())
                .name(gameEntity.getTitle())
                .categories(gameEntity.getCategories()
                        .stream()
                        .map(this::toCategoryRsDto)
                        .toList())
                .build();
    }

    private CategoryRsDto toCategoryRsDto(CategoryEntity categoryEntity) {
        return CategoryRsDto.builder()
                .id(categoryEntity.getId().toString())
                .name(categoryEntity.getName())
                .build();
    }

}
