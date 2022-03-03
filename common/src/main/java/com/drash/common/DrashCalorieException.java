package com.drash.common;

/**
 * Global exception wrapper.
 *
 * @author drashti.dobariya
 */
public class DrashCalorieException extends RuntimeException {
    public DrashCalorieException(String message) {
        super(message);
    }

    public DrashCalorieException(String message, Throwable cause) {
        super(message, cause);
    }

    public DrashCalorieException(Throwable cause) {
        super(cause);
    }
}
