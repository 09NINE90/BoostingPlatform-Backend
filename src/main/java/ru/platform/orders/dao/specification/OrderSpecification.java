package ru.platform.orders.dao.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderEntity_;
import ru.platform.orders.dto.request.OrdersByCreatorRqDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.utils.IBaseSpecificationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OrderSpecification implements IBaseSpecificationUtil<OrderEntity, OrdersByCreatorRqDto> {

    @Override
    public Set<Specification<OrderEntity>> prepareSpecificationSet(OrdersByCreatorRqDto request) {
        return prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByCreatorRqDto>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OrderEntity>>, OrdersByCreatorRqDto>> result = new ArrayList<>();
        result.add(this::prepareCreator);
        result.add(this::prepareGameName);
        result.add(this::prepareStatus);
        result.add(this::preparePrice);
        return result;
    }

    private void prepareCreator(Set<Specification<OrderEntity>> set, OrdersByCreatorRqDto request) {
        UserEntity creator = request.getCreator();
        if (Objects.nonNull(creator)) {
            set.add(fieldEqualTo(creator, OrderEntity_.CREATOR));
        }
    }

    private void prepareGameName(Set<Specification<OrderEntity>> set, OrdersByCreatorRqDto request) {
        String gameName = request.getGameName();
        if (Objects.nonNull(gameName) && !gameName.isEmpty()) {
            set.add(fieldAreLike(gameName, OrderEntity_.GAME_NAME));
        }
    }

    private void prepareStatus(Set<Specification<OrderEntity>> set, OrdersByCreatorRqDto request) {
        if (Objects.nonNull(request.getStatus())) {
            String status = request.getStatus().name();
            set.add(fieldAreLike(status, OrderEntity_.STATUS));
        }
    }

    private void preparePrice(Set<Specification<OrderEntity>> set, OrdersByCreatorRqDto request) {
        OrdersByCreatorRqDto.PriceDto price = request.getPrice();
        if (price != null && price.getPriceFrom() != null && price.getPriceTo() != null) {
            set.add(fieldAreBetween(price.getPriceFrom(), price.getPriceTo(), OrderEntity_.TOTAL_PRICE));
        }
    }
}
