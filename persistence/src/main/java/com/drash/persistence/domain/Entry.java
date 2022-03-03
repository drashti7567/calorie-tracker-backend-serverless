package com.drash.persistence.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;

/**
 * Entity representing a record in the table for emails created by the user.
 *
 * @author drashti.dobariya
 */
@Data
@DynamoDbBean
@EqualsAndHashCode(callSuper = true)
public class Entry extends BaseEntity {

    @Getter(onMethod = @__({ @DynamoDbAttribute("FOOD_NAME") }))
    private String foodName;

    @Getter(onMethod = @__({ @DynamoDbAttribute("CALORIES") }))
    private Float calories;

    @Getter(onMethod = @__({ @DynamoDbAttribute("CREATED_AT") }))
    private LocalDateTime createdAt;

    @Getter(onMethod = @__({ @DynamoDbAttribute("UPDATED_AT") }))
    private LocalDateTime updatedAt;
}
