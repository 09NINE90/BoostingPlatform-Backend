package ru.platform.orders.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dao.GameEntity_;
import ru.platform.games.dao.PlatformEntity;
import ru.platform.games.dao.PlatformEntity_;
import ru.platform.games.enumz.GamePlatform;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderEntity_;
import ru.platform.orders.dto.request.DashboardRqDto;
import ru.platform.utils.IBaseSpecificationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OrderDashboardSpecification implements IBaseSpecificationUtil<OrderEntity, DashboardRqDto> {

    @Override
    public Set<Specification<OrderEntity>> prepareSpecificationSet(DashboardRqDto request) {
        return prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OrderEntity>>, DashboardRqDto>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OrderEntity>>, DashboardRqDto>> result = new ArrayList<>();
        result.add(this::prepareStatus);
        result.add(this::prepareGameNames);
        result.add(this::preparePlatforms);
        result.add(this::prepareTotalPrice);
        return result;
    }

    private void prepareStatus(Set<Specification<OrderEntity>> set, DashboardRqDto request) {
        if (Objects.nonNull(request.getStatus())) {
            String status = request.getStatus().name();
            set.add(fieldAreLike(status, OrderEntity_.STATUS));
        }
    }

    private void prepareGameNames(Set<Specification<OrderEntity>> set, DashboardRqDto request) {
        Set<String> gameNames = request.getGameNames();
        if (Objects.nonNull(gameNames) && !gameNames.isEmpty()) {
            set.add((root, query, criteriaBuilder) -> {
                Join<OrderEntity, GameEntity> gameJoin = root.join(OrderEntity_.GAME, JoinType.LEFT);
                return gameJoin.get(GameEntity_.TITLE).in(gameNames);
            });
        }
    }

    private void preparePlatforms(Set<Specification<OrderEntity>> set, DashboardRqDto request) {
        Set<GamePlatform> gamePlatforms = request.getGamePlatforms();
        if (Objects.nonNull(gamePlatforms) && !gamePlatforms.isEmpty()) {
            set.add((root, query, criteriaBuilder) -> {
                Join<OrderEntity, PlatformEntity> gameJoin = root.join(OrderEntity_.GAME_PLATFORM, JoinType.LEFT);
                return gameJoin.get(PlatformEntity_.TITLE).in(gamePlatforms);
            });
        }
    }

    private void prepareTotalPrice(Set<Specification<OrderEntity>> set, DashboardRqDto request) {
        DashboardRqDto.PriceDto price = request.getTotalPrice();
        if (price != null && price.getPriceFrom() != null && price.getPriceTo() != null) {
            set.add(fieldAreBetween(price.getPriceFrom(), price.getPriceTo(), OrderEntity_.TOTAL_PRICE));
        }
    }
}
