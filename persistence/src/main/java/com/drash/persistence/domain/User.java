package com.drash.persistence.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * Entity representing a record in the table for emails created by the user.
 *
 * @author drashti.dobariya
 */
@Data
@DynamoDbBean
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Getter(onMethod = @__({ @DynamoDbAttribute("NAME") }))
    private String name;

    @Getter(onMethod = @__({ @DynamoDbAttribute("TYPE") }))
    private String type;

    @Getter(onMethod = @__({ @DynamoDbAttribute("PHONE_NUMBER") }))
    private String phoneNumber;

    @Getter(onMethod = @__({ @DynamoDbAttribute("PASSWORD") }))
    private String password;

    @Getter(onMethod = @__({ @DynamoDbAttribute("CALORIE_LIMIT") }))
    private Float calorieLimit;
}
