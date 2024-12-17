package ru.platform.entity.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.entity.ServicesEntity;
import ru.platform.entity.ServicesEntity_;
import ru.platform.entity.GameEntity;
import ru.platform.request.ServicesRequest;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class BaseOrderSpecification implements IBaseSpecification<ServicesEntity, ServicesRequest> {

    @Override
    public Set<Specification<ServicesEntity>> prepareSpecificationSet(ServicesRequest request) {
        return  prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<ServicesEntity>>, ServicesRequest>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<ServicesEntity>>, ServicesRequest>> result = new ArrayList<>();
//        result.add(this::prepareTitle);
        result.add(this::prepareGame);
        result.add(this::prepareCategories);
        return result;
    }

    private void prepareCategories(Set<Specification<ServicesEntity>> set, ServicesRequest request) {
        String categories = request.getCategories();
        if (categories != null && !categories.isEmpty()){
            set.add(categoryFilter(categories, ServicesEntity_.CATEGORIES));
        }
    }

    private void prepareGame(Set<Specification<ServicesEntity>> set, ServicesRequest request) {
        GameEntity game = request.getGame();
        if (Objects.nonNull(game)){
            String title = request.getGame().getTitle();

            if (Objects.nonNull(title) && !title.isEmpty()){
                set.add(gameFilter(title));
            }
        }
    }

    private void prepareTitle(Set<Specification<ServicesEntity>> set, ServicesRequest request) {
        String title = request.getTitle();
        if (!title.isBlank()){
            set.add(fieldAreLike(title, "title"));
        }
    }
}
