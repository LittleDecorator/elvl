package com.acme.elvl.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamUtils {

    public <T> Predicate<T> logFiltered(Predicate<T> predicate, Consumer<T> action) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(action);

        return value -> {
            if (predicate.test(value)) {
                return true;
            } else {
                action.accept(value);
                return false;
            }
        };
    }

}
