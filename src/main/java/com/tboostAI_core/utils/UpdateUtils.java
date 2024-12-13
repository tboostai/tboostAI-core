package com.tboostAI_core.utils;

import java.util.function.Consumer;

public class UpdateUtils {

    // Check if null
    public static <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public static <T extends Number> void applyIfNotNullAndCondition(Consumer<T> setter, T value, boolean condition) {
        applyIfNotNullAndCondition(setter, value, 1.0, condition);
    }

    public static <T extends Number> void applyIfNotNullAndCondition(Consumer<T> setter, T value, double rate, boolean condition) {
        if (value != null && condition) {
            @SuppressWarnings("unchecked")
            T scaledValue = (T) Double.valueOf(value.doubleValue() * rate);
            setter.accept(scaledValue);
        }
    }
}

