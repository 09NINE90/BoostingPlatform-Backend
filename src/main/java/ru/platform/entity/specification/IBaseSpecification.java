package ru.platform.entity.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.platform.entity.ServicesEntity;
import ru.platform.entity.ServicesEntity_;
import ru.platform.entity.GameEntity;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface IBaseSpecification<T, U>  {
    default Specification<T> getFilter(U request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Specification<T> where = this.prepareWhereStatementWithAnd(this.prepareSpecificationSet(request));
            return where == null ? null : where.toPredicate(root, criteriaQuery, criteriaBuilder);
        };
    }

    Set<Specification<T>> prepareSpecificationSet(U var1);

    List<BiConsumer<Set<Specification<T>>, U>> getSpecificationConsumerList();

    default Set<Specification<T>> prepareSpecificationSet(U request, Predicate<U> activateFunction) {
        Set<Specification<T>> specificationSet = new HashSet();
        if (activateFunction.test(request)) {
            this.getSpecificationConsumerList().forEach((c) -> {
                c.accept(specificationSet, request);
            });
        }

        return specificationSet;
    }

    default void addTextFilterEq(Set<Specification<T>> specificationSet, Supplier<String> valueSupplier, String field, Predicate<U> activateFunction, U request) {
        if (activateFunction.test(request)) {
            this.addTextFilterEq(specificationSet, valueSupplier, field);
        }

    }

    default void addTextFilterEq(Set<Specification<T>> specificationSet, Supplier<String> valueSupplier, String field) {
        specificationSet.add(this.fieldEqualTo((String)valueSupplier.get(), field));
    }

    default Specification<T> fieldAreIn(Set<?> setOfValues, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaBuilder.In<Object> inField = criteriaBuilder.in(root.get(field));
            Stream var10000 = setOfValues.stream().filter(Objects::nonNull).filter((e) -> {
                return !e.toString().isEmpty();
            });
            Objects.requireNonNull(inField);
            var10000.forEach(inField::value);
            return inField;
        };
    }

    default Specification<T> fieldAreInIncludeNull(Set<?> setOfValues, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaBuilder.In<Object> inField = criteriaBuilder.in(root.get(field));
            Stream var10000 = setOfValues.stream().filter(Objects::nonNull).filter((e) -> {
                return !e.toString().isEmpty();
            });
            Objects.requireNonNull(inField);
            var10000.forEach(inField::value);
            return criteriaBuilder.or(inField, criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> fieldAreBetween(LocalDate dateFrom, LocalDate dateTo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(field), dateFrom, dateTo);
        };
    }

    default Specification<T> fieldAreBetween(Integer valFrom, Integer valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(field), valFrom, valTwo);
        };
    }

    default Specification<T> fieldAreBetween(Double valFrom, Double valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(field), valFrom, valTwo);
        };
    }

    default Specification<T> fieldAreBetween(Long valFrom, Long valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(field), valFrom, valTwo);
        };
    }

    default Specification<T> fieldAreBetweenIncludeNull(Integer valFrom, Integer valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.between(root.get(field), valFrom, valTwo), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> fieldAreBetweenIncludeNull(Double valFrom, Double valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.between(root.get(field), valFrom, valTwo), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> fieldAreBetweenIncludeNull(Long valFrom, Long valTwo, String field) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.between(root.get(field), valFrom, valTwo), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> fieldNotEqualTo(String value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(criteriaBuilder.upper(root.get(field)), value.toUpperCase());
        };
    }

    default Specification<T> fieldEqualTo(String value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.upper(root.get(field)), value.toUpperCase());
        };
    }

    default Specification<T> fieldEqualTo(Integer value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), value);
        };
    }

    default Specification<T> fieldEqualToIncludeNull(String value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.equal(criteriaBuilder.upper(root.get(field)), value.toUpperCase()), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> greaterThanOrEqualToIncludeNull(Integer value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Integer.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> greaterThanOrEqualToIncludeNull(Double value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Double.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> greaterThanOrEqualToIncludeNull(Long value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Long.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> greaterThanOrEqualTo(Integer value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Integer.class), value);
        };
    }

    default Specification<T> greaterThanOrEqualTo(Double value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Double.class), value);
        };
    }

    default Specification<T> greaterThanOrEqualTo(Long value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field).as(Long.class), value);
        };
    }

    default Specification<T> lessThanOrEqualToIncludeNull(Integer value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(root.get(field).as(Integer.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> lessThanOrEqualToIncludeNull(Double value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(root.get(field).as(Double.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> lessThanOrEqualToIncludeNull(Long value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(root.get(field).as(Long.class), value), criteriaBuilder.isNull(root.get(field)));
        };
    }

    default Specification<T> lessThanOrEqualTo(Integer value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(field).as(Integer.class), value);
        };
    }

    default Specification<T> lessThanOrEqualTo(Double value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(field).as(Double.class), value);
        };
    }

    default Specification<T> categoryFilter(String categoryName, String fieldName) {
        return (root, query, criteriaBuilder) -> {
            // LIKE '%категория%'
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get(fieldName)),
                    "%" + categoryName.toUpperCase() + "%"
            );
        };
    }

    default <V extends Number> Specification<T> fieldNotEqualTo(V value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(root.get(field), value);
        };
    }

    default Specification<T> fieldNotEqualTo(LocalDate value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(root.get(field), value);
        };
    }

    default Specification<T> gameFilter(String gameId){
        return (root, query, criteriaBuilder) -> {
            // Access the game entity using the Join
            Join<ServicesEntity, GameEntity> gameJoin = root.join(ServicesEntity_.GAME);
            return criteriaBuilder.equal(gameJoin.get("title"), gameId);
        };
    }

    default Specification<T> fieldAreLike(String value, String field) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), value.toUpperCase());
        };
    }

    default Specification<T> prepareWhereStatementWithAnd(Set<Specification<T>> specificationSet) {
        Specification<T> where = null;
        Iterator var3 = specificationSet.iterator();

        while(var3.hasNext()) {
            Specification<T> e = (Specification)var3.next();
            if (e != null) {
                if (where == null) {
                    where = Specification.where(e);
                } else {
                    where = where.and(e);
                }
            }
        }

        return where;
    }

    default void addIntNumericRangeFilter(Set<Specification<T>> specificationSet, Long start, Long finish, String field) {
        if (start != null && finish != null) {
            if (start <= 0L && finish > 0L) {
                specificationSet.add(this.fieldAreBetweenIncludeNull(start, finish, field));
            } else {
                specificationSet.add(this.fieldAreBetween(start, finish, field));
            }
        } else if (start != null) {
            specificationSet.add(this.greaterThanOrEqualTo(start, field));
        } else if (finish != null) {
            specificationSet.add(this.lessThanOrEqualToIncludeNull(finish, field));
        }

    }
}
