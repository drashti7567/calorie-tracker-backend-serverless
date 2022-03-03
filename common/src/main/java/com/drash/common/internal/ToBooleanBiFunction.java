package com.drash.common.internal;

public interface ToBooleanBiFunction<T, U> {
    boolean applyAsBoolean(T t, U u);
}