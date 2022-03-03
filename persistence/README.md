# Persistence
This module contains the entity model and repository definition for data persistence to DynamoDB.

## DynamoDB Mapper
We use [DynamoDBMapper](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.html) to map Java POJOs to a DynamoDB table and its attributes.
We follow a [single-table design](https://www.sensedeep.com/blog/posts/2021/dynamodb-singletable-design.html) and entities are distinguished through the `ENTITY_TYPE` attribute.

We use the [java.lang.Class#getSimpleName](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html#getSimpleName()) of each entity as the `ENTITY_TYPE` attribute value.

### Sample Entity
```java
@Data
@DynamoDBTable(tableName = "TABLE_NAME")
public class Role extends BaseEntity  {

    @DynamoDBAttribute(attributeName = "VALUE")
    private String value;
}
```
In the sample entity above, the `ENTITY_TYPE` value will be **Role**.

### DynamoDB Mapper TableName Override
To allow configurable table name between stages (e.g. dev, staging, production), we use DynamoDB Mapper's TableName Override feature to override the table name at runtime.
In the sample entity above, the table name provided is just a placeholder.
```java
@DynamoDBTable(tableName = "TABLE_NAME")
```
This placeholder gets overridden at runtime through an Environment Variable or Java System Property named `TABLE_NAME`.
```java
final var tableName = EnvironmentUtils.getEnv("TABLE_NAME");
this.mapper = new DynamoDBMapper(this.client, DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build());
```