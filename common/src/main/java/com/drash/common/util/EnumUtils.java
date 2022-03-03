package com.drash.common.util;

import java.util.Arrays;

/**
 * Utility object for various enum-related purposes.
 *
 * @author drashti.dobariya
 */
public class EnumUtils {

    /**
     * Gets an enum constant matching the given String value.
     *
     * @param enumClass The enum class
     * @param value The String value
     * @param <E> The runtime type of the Enum
     * @return Matching enum constant
     * @throws IllegalArgumentException If no match found for the given String value
     */
    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> StringUtils.equalsIgnoreCase(e.name(), value))
                .findFirst()
                .orElseThrow(() -> throwException(enumClass, value));
    }

    private static <E extends Enum<E>> IllegalArgumentException throwException(final Class<E> enumClass,
                                                                               final String value) {
        return new IllegalArgumentException(String.format(
                "Provided string value '%s' doesn't match an enum '%s' constant.",
                value, enumClass.getSimpleName()));
    }
}
