package com.drash.lambda.model.response;

import com.drash.lambda.model.response.ImmutableBadRequestResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

/**
 * Response for bad requests.
 *
 * @author drashti.dobariya
 */
@Value.Immutable
@JsonSerialize(as = ImmutableBadRequestResponse.class)
@JsonDeserialize(as = ImmutableBadRequestResponse.class)
public interface BadRequestResponse {
    List<String> messages();
}
