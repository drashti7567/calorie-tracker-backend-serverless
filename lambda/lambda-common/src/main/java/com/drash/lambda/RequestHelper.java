package com.drash.lambda;

import com.drash.common.EnvironmentUtils;
import com.drash.lambda.model.ImmutableUserInfo;
import com.drash.lambda.model.UserInfo;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Helper for getting information from the API Gateway Request Context.
 *
 * @author drashti.dobariya
 */
@Slf4j
@RequiredArgsConstructor
public class RequestHelper {

    private final CognitoIdentityClient amazonCognitoIdentity;

    /**
     * Gets the current user info from the request context.
     *
     * @param requestEvent The API Gateway proxy request context
     * @return UserInfo
     */
    public UserInfo getCurrentUserInfo(APIGatewayProxyRequestEvent requestEvent) {
        final var context = requestEvent.getRequestContext();
        final Map<String, Object> claims = getAuthClaims(context);
        final var username = valueToString(claims.get("cognito:username"));
        final var firstName = valueToString(claims.get("name"));
        final var lastName = valueToString(claims.get("family_name"));
        final var email = valueToString(claims.get("email"));
        final var identityId = getCognitoIdentityId(getAuthorizationHeader(requestEvent));

        return ImmutableUserInfo
                .builder()
                .cognitoIdentityId(identityId)
                .cognitoUsername(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }

    private Map<String, Object> getAuthClaims(final APIGatewayProxyRequestEvent.ProxyRequestContext context) {
        return (Map<String, Object>) context.getAuthorizer().get("claims");
    }

    private String valueToString(final Object object) {
        return Objects.nonNull(object) ? Objects.toString(object) : "";
    }

    private String getAuthorizationHeader(final APIGatewayProxyRequestEvent requestEvent) {
        final var headers = requestEvent.getHeaders();
        return Optional.ofNullable(headers)
                .map(entry -> entry.get("Authorization"))
                .map(value -> value.replace("Bearer ", ""))
                .orElse(null);
    }

    private String getCognitoIdentityId(final String authorization) {
        return Optional.ofNullable(authorization)
                .map(this::requestIdentityId)
                .orElse(null);
    }

    private String requestIdentityId(final String authorization) {
        final var awsRegion = EnvironmentUtils.getEnv("DRASH_AWS_REGION");
        final var cognitoUserPoolId = EnvironmentUtils.getEnv("DRASH_AWS_COGNITO_USERPOOL_ID");
        final var loginKey = String.format("cognito-idp.%s.amazonaws.com/%s", awsRegion, cognitoUserPoolId);

        log.info("Requesting identity id for current authentication.");
        final var getIdRequest = GetIdRequest.builder()
                .accountId(System.getenv("DRASH_AWS_ACCOUNT_ID"))
                .identityPoolId(System.getenv("DRASH_AWS_IDENTITY_POOL_ID"))
                .logins(Map.of(loginKey, authorization))
                .build();
        return amazonCognitoIdentity.getId(getIdRequest).identityId();
    }
}
