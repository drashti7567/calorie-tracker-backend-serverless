package com.drash.persistence;

import com.drash.persistence.domain.Entry;
import com.drash.persistence.domain.User;
import com.drash.persistence.repo.EntryRepo;
import com.drash.persistence.repo.UserRepo;
import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.inject.Singleton;

/**
 * Dagger module defining the provider method bindings for singletons used in
 * persistence-related functionality.
 *
 * @author drashti.dobariya
 */
@Module
public class PersistenceModule {

    @Provides
    @Singleton
    public DynamoDbClient amazonDynamoDB(Region region, SdkHttpClient httpClient) {
        return DynamoDbClient.builder()
                .region(region)
                .httpClient(httpClient)
                .build();
    }

    @Provides
    @Singleton
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Provides
    @Singleton
    public EntryRepo entryRepo(DynamoDbEnhancedClient client,
                               TableSchema<Entry> schema) {
        return new EntryRepo(client, schema);
    }

    @Provides
    @Singleton
    public TableSchema<Entry> entryTableSchema() {
        return TableSchema.fromBean(Entry.class);
    }

    @Provides
    @Singleton
    public UserRepo userRepo(DynamoDbEnhancedClient client,
                             TableSchema<User> schema) {
        return new UserRepo(client, schema);
    }

    @Provides
    @Singleton
    public TableSchema<User> userTableSchema() {
        return TableSchema.fromBean(User.class);
    }

}
