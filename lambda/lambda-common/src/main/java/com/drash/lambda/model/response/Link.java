package com.drash.lambda.model.response;

import com.drash.lambda.model.response.ImmutableLink;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableLink.class)
@JsonDeserialize(as = ImmutableLink.class)
public interface Link {
    String rel();
    String href();
}
