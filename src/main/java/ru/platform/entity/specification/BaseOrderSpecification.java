package ru.platform.entity.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.entity.GameEntity;
import ru.platform.request.BaseOrderEditRequest;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class BaseOrderSpecification implements IBaseSpecification<BaseOrdersEntity, BaseOrderEditRequest> {

    @Override
    public Set<Specification<BaseOrdersEntity>> prepareSpecificationSet(BaseOrderEditRequest request) {
        return  prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<BaseOrdersEntity>>, BaseOrderEditRequest>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<BaseOrdersEntity>>, BaseOrderEditRequest>> result = new ArrayList<>();
        result.add(this::prepareTitle);
        result.add(this::prepareGame);
        return result;
    }

    private void prepareGame(Set<Specification<BaseOrdersEntity>> set, BaseOrderEditRequest request) {
        GameEntity game = request.getGame();
        if (Objects.nonNull(game)){
            String title = request.getGame().getTitle();

            if (Objects.nonNull(title) && !title.isEmpty()){
                set.add(gameFilter(title));
            }
        }
    }

    private void prepareTitle(Set<Specification<BaseOrdersEntity>> set, BaseOrderEditRequest request) {
        String title = request.getTitle();
        if (Objects.nonNull(title) && !title.isEmpty()){
            set.add(fieldAreLike(title, "title"));
        }
    }
}
