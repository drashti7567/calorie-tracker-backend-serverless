package com.drash.persistence.domain;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Base entity model.
 *
 * @author drashti.dobariya
 */
@Data
@DynamoDbBean
public abstract class BaseEntity {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({
            @DynamoDbSortKey,
            @DynamoDbAttribute("SK"),
    }))
    private String sk;

    @Getter(onMethod = @__({
            @DynamoDbAttribute("ENTITY_TYPE"),
            @DynamoDbSecondaryPartitionKey(indexNames = { "IDX_ENTITY_TYPE" }),
    }))
    private String entityType;

    @Getter(onMethod = @__({
            @DynamoDbAttribute("GS1_PK"),
            @DynamoDbSecondaryPartitionKey(indexNames = { "GS1" }),
    }))
    private String gs1pk;

    @Getter(onMethod = @__({
            @DynamoDbAttribute("GS1_SK"),
            @DynamoDbSecondarySortKey(indexNames = { "GS1" }),
    }))
    private String gs1sk;

    public BaseEntity() {
        setEntityType(getClass().getSimpleName());
    }
}
