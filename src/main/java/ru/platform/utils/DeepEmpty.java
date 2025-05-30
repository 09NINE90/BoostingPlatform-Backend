package ru.platform.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.function.Function;

@UtilityClass
class DeepEmpty {

    <T, V> boolean isDeepEmpty(T dto, Function<T, V> getter) {
        V value = SafelyGet.safelyGet(dto, getter);

        if (value == null) return true;

        return value instanceof Collection<?> && ((Collection<?>) value).isEmpty();
    }

    <T, V, U> boolean isDeepEmpty(T dto, Function<T, V> firstGetter, Function<V, U> secondGetter) {
        U secondFieldValue = SafelyGet.safelyGet(dto, firstGetter, secondGetter);

        if (secondFieldValue == null) return true;

        return secondFieldValue instanceof Collection<?> && ((Collection<?>) secondFieldValue).isEmpty();
    }
}
