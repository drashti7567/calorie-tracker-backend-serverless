package com.drash.lambda.model;

import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public interface UserInfo {
    String cognitoIdentityId();
    String cognitoUsername();

    @Nullable String firstName();
    @Nullable String lastName();

    String email();
}
