package ru.platform.entity.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.entity.BaseOrdersEntity_;
import ru.platform.entity.GameEntity;
import ru.platform.request.BaseOrderRequest;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class BaseOrderSpecification implements IBaseSpecification<BaseOrdersEntity, BaseOrderRequest> {

    @Override
    public Set<Specification<BaseOrdersEntity>> prepareSpecificationSet(BaseOrderRequest request) {
        return  prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<BaseOrdersEntity>>, BaseOrderRequest>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<BaseOrdersEntity>>, BaseOrderRequest>> result = new ArrayList<>();
//        result.add(this::prepareTitle);
        result.add(this::prepareGame);
        result.add(this::prepareCategories);
        return result;
    }

    private void prepareCategories(Set<Specification<BaseOrdersEntity>> set, BaseOrderRequest request) {
        String categories = request.getCategories();
        if (categories != null && !categories.isEmpty()){
            set.add(categoryFilter(categories, BaseOrdersEntity_.CATEGORIES));
        }
    }

    private void prepareGame(Set<Specification<BaseOrdersEntity>> set, BaseOrderRequest request) {
        GameEntity game = request.getGame();
        if (Objects.nonNull(game)){
            String title = request.getGame().getTitle();

            if (Objects.nonNull(title) && !title.isEmpty()){
                set.add(gameFilter(title));
            }
        }
    }

    private void prepareTitle(Set<Specification<BaseOrdersEntity>> set, BaseOrderRequest request) {
        String title = request.getTitle();
        if (!title.isBlank()){
            set.add(fieldAreLike(title, "title"));
        }
    }
}
