package com.drash.lambda.model.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Value.Immutable
@JsonSerialize(as = ImmutableEntryModel.class)
@JsonDeserialize(as = ImmutableEntryModel.class)
public interface EntryModel {

    String entryId();
    String eatTime();
    String userEmail();
    String foodName();
    Float calories();
    String userName();
    LocalDateTime creationTimeStamp();
    LocalDateTime lastUpdatedTimestamp();
}
