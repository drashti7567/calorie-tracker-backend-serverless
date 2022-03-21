package com.drash.persistence.repo;


import com.drash.common.util.Ensure;
import com.drash.persistence.domain.Entry;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Entry> listByUserEmail(final String email) {
        final var emailPk = Ensure.nonBlank(email, "Email key must not be blank.");

        final var index = getEntityTypeIndex();
        final var queryEnhancedRequest = QueryEnhancedRequest
                .builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(emailPk).build()))
                .consistentRead(false)
                .build();

        return table.query(queryEnhancedRequest)
                .items()
                .stream()
                .collect(Collectors.toList());
    }
}
