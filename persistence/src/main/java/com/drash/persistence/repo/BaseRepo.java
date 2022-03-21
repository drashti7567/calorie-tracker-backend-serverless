package com.drash.persistence.repo;

import com.drash.common.DrashCalorieException;
import com.drash.common.EnvironmentUtils;
import com.drash.persistence.domain.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchGetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ReadBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Base repository.
 *
 * @author drashti.dobariya
 */
@Slf4j
public abstract class BaseRepo<E extends BaseEntity> {
    protected final DynamoDbEnhancedClient client;
    protected final Class<E> entityClass;
    protected final DynamoDbTable<E> table;

    public BaseRepo(DynamoDbEnhancedClient dynamoDbEnhancedClient,
                    TableSchema<E> tableSchema) {
        final var type = (ParameterizedType) getClass().getGenericSuperclass();
        this.client = dynamoDbEnhancedClient;
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];

        final var tableName = EnvironmentUtils.getEnv("TABLE_NAME");
        this.table = client.table(tableName, tableSchema);
    }

    /**
     * @return The entity type
     */
    public final String getEntityType() {
        return entityClass.getSimpleName();
    }

    /**
     * Save or update the given entity.
     *
     * @param entity The entity to save
     * @return The entity
     */
    public final E save(final E entity) {
        table.putItem(entity);
        return entity;
    }

    /**
     * Batch save the given list of entities.
     *
     * @param entities The entities to save
     */
    public final void batchSave(final List<E> entities) {
        final var writeBatchBuilder = WriteBatch.builder(entityClass);
        entities.forEach(writeBatchBuilder::addPutItem);

        final var batchWriteItemRequest = BatchWriteItemEnhancedRequest
                .builder()
                .addWriteBatch(writeBatchBuilder.build())
                .build();
        this.client.batchWriteItem(batchWriteItemRequest);
    }

    /**
     * Deletes the given entity.
     *
     * @param entity The entity to delete
     */
    public final void delete(final E entity) {
        this.table.deleteItem(entity);
    }

    /**
     * Gets an entity by it's primary key.
     *
     * @param pk The partition key value
     * @param sk The sort/range key value
     * @return Entity identified by the given key values
     */
    public final Optional<E> get(final String pk, final String sk) {
        return Optional.ofNullable(this.table.getItem(
                Key.builder()
                        .partitionValue(pk)
                        .sortValue(sk).build()));
    }

    /**
     * Gets an entity by it's primary key.
     *
     * @param pk The partition key value
     * @return Entity identified by the given key values
     */
    public final Optional<E> get(final String pk) {
        return Optional.ofNullable(this.table.getItem(
                Key.builder()
                        .partitionValue(pk)
                        .build()));
    }

    /**
     * Gets an instance of entity with the given Hash/Partition key and Sort/Range key.
     * If none is found, creates a new instance instead.
     *
     * @param pk The hash/partition key
     * @param sk The sort/range key
     * @return Entity instance
     */
    public final E getOrCreate(final String pk, final String sk) {
        final Optional<E> entity = get(pk, sk);
        if (entity.isEmpty()) {
            try {
                return entityClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new DrashCalorieException("Unable to create new instance of entity class: " + entityClass.getName());
            }
        }
        return entity.get();
    }

    /**
     * List all instances of the entity managed by this repository.
     *
     * @return List of entity
     */
    public final List<E> query() {
        final var queryConditional = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(getEntityType()).build());
        final var queryEnhancedRequest = QueryEnhancedRequest
                .builder()
                .consistentRead(false)
                .queryConditional(queryConditional)
                .build();

        return queryIndex(getEntityTypeIndex(), queryEnhancedRequest);
    }

    protected Expression getEntityTypeFilterExpression() {
        return Expression.builder()
                .expression("ENTITY_TYPE = :entityType")
                .putExpressionValue(":entityType", AttributeValue.builder().s(getEntityType()).build())
                .build();
    }

    protected DynamoDbIndex<E> getEntityTypeIndex() {
        return getIndex("IDX_ENTITY_TYPE");
    }

    protected DynamoDbIndex<E> getIndex(final String indexName) {
        return table.index(indexName);
    }

    protected final List<E> queryIndex(final DynamoDbIndex<E> index,
                                       final QueryEnhancedRequest queryEnhancedRequest) {
        final var results = index.query(queryEnhancedRequest)
                .stream()
                .findFirst()
                .map(Page::items)
                .orElse(Collections.emptyList());

        if (!results.isEmpty()) {
            final var readBatch = ReadBatch.builder(entityClass)
                    .mappedTableResource(this.table);
            results.forEach(readBatch::addGetItem);

            final var request = BatchGetItemEnhancedRequest.builder()
                    .addReadBatch(readBatch.build())
                    .build();
            final var batchGetResults = client.batchGetItem(request);
            return batchGetResults
                    .stream()
                    .findFirst()
                    .map(e -> e.resultsForTable(this.table))
                    .orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }

}
