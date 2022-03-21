package com.drash.lambda.model.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutablePaginatedResult.class)
@JsonDeserialize(as = ImmutablePaginatedResult.class)
public interface PaginatedResult<E> {

    List<E> items();
    int itemsPerPage();
    @Nullable Map<String, String> lastEvaluatedKey();
}
