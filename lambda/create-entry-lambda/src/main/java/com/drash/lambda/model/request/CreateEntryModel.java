package com.drash.lambda.model.request;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import com.drash.lambda.model.request.validation.Validatable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableCreateEntryModel.class)
@JsonDeserialize(as = ImmutableCreateEntryModel.class)
public interface CreateEntryModel extends Validatable<CreateEntryModel> {
    @NotNull String userEmail();
    @NotNull String foodName();
    @NotNull String eatingTime();
    @NotNull Float calories();

    @Override
    default Validator<CreateEntryModel> validator() {
        return ValidatorBuilder.<CreateEntryModel>of()
                .failFast(true)
                .build();
    }
}
