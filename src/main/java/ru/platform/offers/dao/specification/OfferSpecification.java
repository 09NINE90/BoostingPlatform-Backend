package ru.platform.offers.dao.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.platform.offers.dto.request.OfferFilterRqDto;
import ru.platform.utils.IBaseSpecificationUtil;
import ru.platform.offers.dao.OfferEntity;
import ru.platform.offers.dao.OfferEntity_;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OfferSpecification implements IBaseSpecificationUtil<OfferEntity, OfferFilterRqDto> {

    @Override
    public Set<Specification<OfferEntity>> prepareSpecificationSet(OfferFilterRqDto request) {
        return  prepareSpecificationSet(request, Objects::nonNull);
    }

    @Override
    public List<BiConsumer<Set<Specification<OfferEntity>>, OfferFilterRqDto>> getSpecificationConsumerList() {
        List<BiConsumer<Set<Specification<OfferEntity>>, OfferFilterRqDto>> result = new ArrayList<>();
        result.add(this::prepareIsActive);
        result.add(this::prepareGame);
        result.add(this::prepareCategory);
        return result;
    }

    private void prepareIsActive(Set<Specification<OfferEntity>> set, OfferFilterRqDto request) {
        set.add(fieldEqualTo(true, OfferEntity_.IS_ACTIVE));
    }

    private void prepareGame(Set<Specification<OfferEntity>> set, OfferFilterRqDto request) {
        String gameId = request.getGameId();
        if (Objects.nonNull(gameId) && !gameId.isEmpty()){
            set.add(gameFilter(gameId, OfferEntity_.GAME));
        }
    }

    private void prepareCategory(Set<Specification<OfferEntity>> set, OfferFilterRqDto request) {
        String categories = request.getCategory();
        if (categories != null && !categories.isEmpty()){
            set.add(categoryFilter(categories, OfferEntity_.CATEGORIES));
        }
    }

}
