package com.drash.aws;

import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;

import javax.inject.Singleton;

@Module
public class AwsCoreModule {

    @Provides
    @Singleton
    public Region region() {
        return Region.of(System.getenv("AWS_REGION"));
    }

    @Provides
    @Singleton
    public SdkHttpClient httpClient() {
        return UrlConnectionHttpClient.builder().build();
    }
}
