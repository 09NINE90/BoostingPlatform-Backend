package ru.platform.orders.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dao.GameEntity_;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderEntity_;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.utils.IBaseSpecificationUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersByBoosterSpecification implements IBaseSpecificationUtil<OrderEntity, OrdersByBoosterRqDto> {

    @Override
    public Set<Specification<OrderEntity>> prepareSpecificationSet(OrdersByBoosterRqDto request) {
        return prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByBoosterRqDto>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByBoosterRqDto>> result = new ArrayList<>();
        result.add(this::prepareBooster);
        result.add(this::prepareGameNames);
        result.add(this::preparePlatforms);
        result.add(this::prepareStatus);
        result.add(this::prepareBoosterPrice);
        return result;
    }

    private void prepareBooster(Set<Specification<OrderEntity>> set, OrdersByBoosterRqDto request) {
        UserEntity booster = request.getBooster();
        if (Objects.nonNull(booster)) {
            set.add(fieldEqualTo(booster, OrderEntity_.BOOSTER));
        }
    }

    private void prepareStatus(Set<Specification<OrderEntity>> set, OrdersByBoosterRqDto request) {
        if (Objects.nonNull(request.getStatuses()) && !request.getStatuses().isEmpty()) {
            Set<String> statuses = request.getStatuses().stream().map(Enum::name).collect(Collectors.toSet());
            set.add(fieldAreIn(statuses, OrderEntity_.STATUS));
        }
    }

    private void prepareGameNames(Set<Specification<OrderEntity>> set, OrdersByBoosterRqDto request) {
        Set<String> gameNames = request.getGameNames();
        if (Objects.nonNull(gameNames) && !gameNames.isEmpty()) {
            set.add((root, query, criteriaBuilder) -> {
                Join<OrderEntity, GameEntity> gameJoin = root.join(OrderEntity_.GAME, JoinType.LEFT);
                return gameJoin.get(GameEntity_.TITLE).in(gameNames);
            });
        }
    }

    private void preparePlatforms(Set<Specification<OrderEntity>> set, OrdersByBoosterRqDto request) {
        Set<String> gamePlatforms = request.getGamePlatforms();
        if (Objects.nonNull(gamePlatforms) && !gamePlatforms.isEmpty()) {
            set.add(fieldAreIn(gamePlatforms, OrderEntity_.GAME_PLATFORM));
        }
    }

    private void prepareBoosterPrice(Set<Specification<OrderEntity>> set, OrdersByBoosterRqDto request) {
        OrdersByBoosterRqDto.PriceDto price = request.getPrice();
        if (price != null && price.getPriceFrom() != null && price.getPriceTo() != null) {
            set.add(fieldAreBetween(price.getPriceFrom(), price.getPriceTo(), OrderEntity_.BOOSTER_SALARY));
        }
    }

}
