package ru.platform.utils;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@UtilityClass
public class DtoUtil {

    public <T, U> U safelyGet(T dto, Function<T, U> getter) {
        return SafelyGet.safelyGet(dto, getter);
    }

    public <T, V> boolean isDeepEmpty(T dto, Function<T, V> getter) {
        return DeepEmpty.isDeepEmpty(dto, getter);
    }

    public <T, V, U> boolean isDeepEmpty(T dto, Function<T, V> firstGetter, Function<V, U> secondGetter) {
        return DeepEmpty.isDeepEmpty(dto, firstGetter, secondGetter);
    }

    public <T, U> List<U> safelyGetList(T dto, Function<T, ? extends List<U>> listGetter) {
        return Objects.requireNonNullElseGet(safelyGet(dto, listGetter), Collections::emptyList);
    }
}
