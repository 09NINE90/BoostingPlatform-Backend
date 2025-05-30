package ru.platform.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Function;

@UtilityClass
class SafelyGet {

    static final String NULL_GETTER_ERROR_MESSAGE = "The getter returned a null value";

    <T, U> U safelyGet(T dto, Function<T, U> getter) {
        Objects.requireNonNull(getter, NULL_GETTER_ERROR_MESSAGE);
        if (dto == null) return null;
        return getter.apply(dto);
    }

    <T, V, U> U safelyGet(T dto, Function<T, V> firstGetter, Function<V, U> secondGetter) {
        Objects.requireNonNull(firstGetter, NULL_GETTER_ERROR_MESSAGE);
        Objects.requireNonNull(secondGetter, NULL_GETTER_ERROR_MESSAGE);

        if (dto == null) return null;

        V firstFieldValue = firstGetter.apply(dto);
        if (firstFieldValue == null) return null;

        return secondGetter.apply(firstFieldValue);
    }

}
