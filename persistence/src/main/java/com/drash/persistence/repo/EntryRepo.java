package com.drash.persistence.repo;


import com.drash.persistence.domain.Entry;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * Repository for managing entries.
 *
 * @author drashti.dobariya
 */
public class EntryRepo extends BaseRepo<Entry> {

    public EntryRepo(DynamoDbEnhancedClient dynamoDbEnhancedClient,
                     TableSchema<Entry> tableSchema) {
        super(dynamoDbEnhancedClient, tableSchema);
    }
}
