package com.drash.common;

/**
 * Thrown whenever a URI is deemed invalid.
 *
 * @author drashti.dobariya
 */
public class InvalidUriException extends DrashCalorieException {
    public InvalidUriException(String message) {
        super(message);
    }

    public InvalidUriException(String message, Throwable cause) {
        super(message, cause);
    }
}
