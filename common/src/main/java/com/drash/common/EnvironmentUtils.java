package com.drash.common;

import java.util.Optional;

/**
 * Utility object for reading environment/system properties.
 *
 * @author drashti.dobariya
 */
public class EnvironmentUtils {

    /**
     * Get value for the given environment key.
     *
     * @param key The key
     * @return the value
     */
    public static String getEnv(final String key) {
        return getEnv(key, null);
    }

    /**
     * Get value for the given environment key, specifying a default value if key not existing.
     *
     * @param key The key
     * @param defaultValue Default value
     * @return the value
     */
    public static String getEnv(final String key, final String defaultValue) {
        final var value = System.getenv(key);
        return Optional
                .ofNullable(value)
                .orElse(System.getProperty(key, defaultValue));
    }
}
