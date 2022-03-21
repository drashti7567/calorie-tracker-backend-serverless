package com.drash.lambda;

import com.drash.common.CommonModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;

import javax.inject.Singleton;

@Module(includes = { CommonModule.class })
public class LambdaCommonModule {

    @Provides
    @Singleton
    public CognitoIdentityClient amazonCognitoIdentity(Region region,
                                                       SdkHttpClient sdkHttpClient) {
        return CognitoIdentityClient.builder()
                .region(region)
                .httpClient(sdkHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper(ObjectMapper objectMapper) {
        return new APIGatewayProxyResponseEventHelper(objectMapper);
    }

    @Provides
    @Singleton
    public RequestHelper requestHelper(CognitoIdentityClient cognitoIdentityClient) {
        return new RequestHelper(cognitoIdentityClient);
    }
}
