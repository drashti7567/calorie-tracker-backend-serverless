package com.drash.lambda.mapper;

public class ModelMappingException extends RuntimeException {
    public ModelMappingException(String message) {
        super(message);
    }

    public ModelMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelMappingException(Throwable cause) {
        super(cause);
    }
}
