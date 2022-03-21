package com.drash.lambda.handler;

import com.drash.lambda.APIGatewayProxyResponseEventHelper;
import com.drash.lambda.model.request.validation.Validatable;
import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Generic base class for request handlers.
 *
 * @param <I> Input type
 *
 * @author drashti.dobariya
 */
@Slf4j
public abstract class BaseRequestHandler<I> implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Class<I> inputClass;

    @Inject ObjectMapper mapper;
    @Inject APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper;

    public BaseRequestHandler() {
        final var parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        inputClass = (Class<I>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, final Context context) {
        log.info("Processing event: {}", event.getBody());

        try {
            final I input = mapper.readValue(event.getBody(), inputClass);
            final List<ConstraintViolation> violations = new ArrayList<>();
            if (input instanceof Validatable) {
                final Validatable<I> validatable = (Validatable<I>) input;
                final ConstraintViolations constraintViolations = validatable.validator().validate(input);
                if (!constraintViolations.isValid()) {
                    violations.addAll(constraintViolations.violations());
                }
            }
            final var additionalViolations = additionalValidation(event, input);
            if (!violations.isEmpty() || !additionalViolations.isEmpty()) {
                final var errorResponse = apiGatewayProxyResponseEventHelper
                        .createErrorResponse(violations, additionalViolations);
                log.warn("Request validation failed: {}", errorResponse.getBody());
                return errorResponse;
            }

            final Object output = process(input, event);
            return apiGatewayProxyResponseEventHelper.createResponse(output);
        } catch (Exception e) {
            log.error("Unexpected error occurred while processing request.", e);
            return apiGatewayProxyResponseEventHelper.createErrorResponse(e);
        }
    }

    /**
     * Can be overridden to provide additional validation from the default Bean validation.
     *
     * @param event The {@link APIGatewayProxyRequestEvent}
     * @param input The input model
     * @return Set of additional validation messages
     */
    protected Set<String> additionalValidation(APIGatewayProxyRequestEvent event, I input) {
        return Collections.emptySet();
    }

    protected abstract Object process(I input, APIGatewayProxyRequestEvent event) throws Exception;

}
