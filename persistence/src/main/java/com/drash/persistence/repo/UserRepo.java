package com.drash.persistence.repo;

import com.drash.persistence.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * Repository for managing users.
 *
 * @author drashti.dobariya
 */
public class UserRepo extends BaseRepo<User> {

    public UserRepo(DynamoDbEnhancedClient dynamoDbEnhancedClient,
                     TableSchema<User> tableSchema) {
        super(dynamoDbEnhancedClient, tableSchema);
    }
}
