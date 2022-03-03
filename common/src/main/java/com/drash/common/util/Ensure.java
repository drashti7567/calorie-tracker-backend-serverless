package com.drash.common.util;

import java.util.Objects;

/**
 * Utility object for ensuring correct values / state.
 * Can be used for validating method arguments prior to logic invocation.
 *
 * @author drashti.dobariya
 */
public final class Ensure {

    //Private constructor to prevent instantiation
    private Ensure() {
        //intentionally empty
    }

    /**
     * Ensures that the given String value is not null nor blank.
     *
     * @param value The value to check
     * @return The value if it passes validation
     * @throws IllegalArgumentException If value failed validation
     */
    public static String nonBlank(String value) {
        return nonBlank(value, "Provided value must not be blank.");
    }

    /**
     * Ensures that the given String value is not null nor blank.
     *
     * @param value The value to check
     * @param message The message to be set on the exception if value fails validation
     * @return The value if it passes validation
     * @throws IllegalArgumentException If value failed validation
     */
    public static String nonBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw createIllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Ensures that the given value is not null.
     *
     * @param value The value to check
     * @return The value if it passes validation
     * @throws IllegalArgumentException If value failed validation
     */
    public static <T> T nonNull(T value) {
        return nonNull(value, "Provided value must not be null.");
    }

    /**
     * Ensures that the given value is not null.
     *
     * @param value The value to check
     * @param message The message to be set on the exception if value fails validation
     * @return The value if it passes validation
     * @throws IllegalArgumentException If value failed validation
     */
    public static <T> T nonNull(T value, String message) {
        if (Objects.isNull(value)) {
            throw createIllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Ensures that the given condition is truthy.
     *
     * @param condition The condition or boolean flag to evaluate
     * @param message The message to be set on the exception if value fails evaluation
     */
    public static void isTrue(final boolean condition, final String message) {
        if (!condition) {
            throw createIllegalArgumentException(message);
        }
    }

    private static IllegalArgumentException createIllegalArgumentException(String message) {
        return new IllegalArgumentException(message);
    }
}
