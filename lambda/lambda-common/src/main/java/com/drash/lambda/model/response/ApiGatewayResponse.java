package com.drash.lambda.model.response;

import com.drash.lambda.model.response.ImmutableApiGatewayResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.Instant;
import java.util.Map;

/**
 * Wrapper object for APIGateway responses.
 * @param <T> The type of data contained
 *
 * @author drashti.dobariya
 */
@Value.Immutable
@JsonSerialize(as = ImmutableApiGatewayResponse.class)
@JsonDeserialize(as = ImmutableApiGatewayResponse.class)
public interface ApiGatewayResponse<T> {

    /**
     * Default response headers
     */
    Map<String, String> HEADERS = Map.of(
            "Access-Control-Allow-Origin", "*",
            "Access-Control-Allow-Credentials", "true");

    @JsonProperty
    default Instant responseDate() {
        return Instant.now();
    }

    T data();
}
