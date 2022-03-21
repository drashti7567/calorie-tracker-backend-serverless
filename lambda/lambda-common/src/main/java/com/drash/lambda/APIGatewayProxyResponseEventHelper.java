package com.drash.lambda;

import com.drash.lambda.model.response.ImmutableApiGatewayResponse;
import com.drash.lambda.model.response.ImmutableBadRequestResponse;
import com.drash.lambda.model.response.ApiGatewayResponse;
import am.ik.yavi.core.ConstraintViolation;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper object for creating instances of {@link APIGatewayProxyResponseEvent}.
 *
 * @author drashti.dobariya
 */
@Slf4j
@RequiredArgsConstructor
public class APIGatewayProxyResponseEventHelper {

    private final ObjectMapper objectMapper;

    /**
     * @param body The response body
     * @return A success {@link APIGatewayProxyResponseEvent}
     */
    public APIGatewayProxyResponseEvent createResponse(final Object body) {
        final var response = ImmutableApiGatewayResponse
                .builder()
                .data(body)
                .build();
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.OK)
                .withBody(mapToString(response))
                .withHeaders(ApiGatewayResponse.HEADERS);
    }

    /**
     * @param exception The exception encountered
     * @return An {@link APIGatewayProxyResponseEvent} for the given exception
     */
    public APIGatewayProxyResponseEvent createErrorResponse(final Exception exception) {
        final var body = ImmutableApiGatewayResponse
                .builder()
                .data(exception.getMessage())
                .build();
        return new APIGatewayProxyResponseEvent()
                .withHeaders(ApiGatewayResponse.HEADERS)
                .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                .withBody(mapToString(body));
    }

    /**
     * @param messages List of messages
     * @return APIGatewayProxyResponseEvent with status {@link HttpStatusCode#BAD_REQUEST}
     */
    public APIGatewayProxyResponseEvent createBadRequestResponse(final List<String> messages) {
        final var body = ImmutableApiGatewayResponse
                .builder()
                .data(ImmutableBadRequestResponse.builder()
                        .messages(messages)
                        .build())
                .build();
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST)
                .withBody(mapToString(body));
    }

    /**
     * @param constraintViolations Set of {@link ConstraintViolation constraint violations}
     * @return An {@link APIGatewayProxyResponseEvent} for the given constraint violations
     */
    public <I> APIGatewayProxyResponseEvent createErrorResponse(
            final List<ConstraintViolation> constraintViolations) {
        return createErrorResponse(constraintViolations, Collections.emptySet());
    }

    /**
     * @param constraintViolations Set of {@link ConstraintViolation constraint violations}
     * @param additionalMessages Additional error messages
     * @return An {@link APIGatewayProxyResponseEvent} for the given constraint violations
     */
    public <I> APIGatewayProxyResponseEvent createErrorResponse(
            final List<ConstraintViolation> constraintViolations,
            final Set<String> additionalMessages) {
        final List<String> messages = buildValidationMessages(constraintViolations);
        if (Objects.nonNull(additionalMessages) && !additionalMessages.isEmpty()) {
            messages.addAll(additionalMessages);
        }

        final var body = ImmutableApiGatewayResponse
                .builder()
                .data(ImmutableBadRequestResponse.builder()
                        .messages(messages)
                        .build())
                .build();
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST)
                .withBody(mapToString(body));
    }

    private <I> List<String> buildValidationMessages(
            final List<ConstraintViolation> constraintViolations) {
        return constraintViolations.stream()
                .map(ConstraintViolation::message)
                .collect(Collectors.toList());
    }

    private String mapToString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Unable to write object to String using ObjectMapper.", e);
            /*
             * Default to empty String. This should never be invoked unless something
             * is wrong with underlying mapping configurations
             */
            return "";
        }
    }
}
