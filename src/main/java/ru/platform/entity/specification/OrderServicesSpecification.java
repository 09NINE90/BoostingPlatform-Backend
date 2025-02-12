package ru.platform.entity.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.entity.OrderServicesEntity;
import ru.platform.entity.OrderServicesEntity_;
import ru.platform.entity.GameEntity;
import ru.platform.request.OrderServicesRequest;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OrderServicesSpecification implements IBaseSpecification<OrderServicesEntity, OrderServicesRequest> {

    @Override
    public Set<Specification<OrderServicesEntity>> prepareSpecificationSet(OrderServicesRequest request) {
        return  prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OrderServicesEntity>>, OrderServicesRequest>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OrderServicesEntity>>, OrderServicesRequest>> result = new ArrayList<>();
        result.add(this::prepareGame);
        result.add(this::prepareCategories);
        return result;
    }

    private void prepareCategories(Set<Specification<OrderServicesEntity>> set, OrderServicesRequest request) {
        String categories = request.getCategories();
        if (categories != null && !categories.isEmpty()){
            set.add(categoryFilter(categories, OrderServicesEntity_.CATEGORIES));
        }
    }

    private void prepareGame(Set<Specification<OrderServicesEntity>> set, OrderServicesRequest request) {
        GameEntity game = request.getGame();
        if (Objects.nonNull(game)){
            String title = request.getGame().getTitle();

            if (Objects.nonNull(title) && !title.isEmpty()){
                set.add(gameFilter(title));
            }
        }
    }

    private void prepareTitle(Set<Specification<OrderServicesEntity>> set, OrderServicesRequest request) {
        String title = request.getTitle();
        if (!title.isBlank()){
            set.add(fieldAreLike(title, "title"));
        }
    }
}
