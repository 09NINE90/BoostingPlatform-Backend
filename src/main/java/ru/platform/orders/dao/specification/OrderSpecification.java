package ru.platform.orders.dao.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderEntity_;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.utils.IBaseSpecificationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OrderSpecification implements IBaseSpecificationUtil<OrderEntity, OrdersByFiltersRqDto> {

    @Override
    public Set<Specification<OrderEntity>> prepareSpecificationSet(OrdersByFiltersRqDto request) {
        return prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByFiltersRqDto>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByFiltersRqDto>> result = new ArrayList<>();
        result.add(this::prepareCreator);
        result.add(this::prepareBooster);
        result.add(this::prepareGameName);
        result.add(this::prepareStatus);
        result.add(this::preparePrice);
        result.add(this::preparePlatform);
        return result;
    }

    private void prepareBooster(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        UserEntity booster = request.getBooster();
        if (Objects.nonNull(booster)) {
            set.add(fieldEqualTo(booster, OrderEntity_.BOOSTER));
        }
    }

    private void preparePlatform(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        String gamePlatform = request.getGamePlatform();
        if (Objects.nonNull(gamePlatform) && !gamePlatform.isEmpty()) {
            set.add(fieldAreLike(gamePlatform, OrderEntity_.GAME_PLATFORM));
        }
    }

    private void prepareCreator(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        UserEntity creator = request.getCreator();
        if (Objects.nonNull(creator)) {
            set.add(fieldEqualTo(creator, OrderEntity_.CREATOR));
        }
    }

    private void prepareGameName(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        String gameName = request.getGameName();
        if (Objects.nonNull(gameName) && !gameName.isEmpty()) {
            set.add(fieldAreLike(gameName, OrderEntity_.GAME_NAME));
        }
    }

    private void prepareStatus(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        if (Objects.nonNull(request.getStatus())) {
            String status = request.getStatus().name();
            set.add(fieldAreLike(status, OrderEntity_.STATUS));
        }
    }

    private void preparePrice(Set<Specification<OrderEntity>> set, OrdersByFiltersRqDto request) {
        OrdersByFiltersRqDto.PriceDto price = request.getPrice();
        if (price != null && price.getPriceFrom() != null && price.getPriceTo() != null) {
            set.add(fieldAreBetween(price.getPriceFrom(), price.getPriceTo(), OrderEntity_.TOTAL_PRICE));
        }
    }
}
