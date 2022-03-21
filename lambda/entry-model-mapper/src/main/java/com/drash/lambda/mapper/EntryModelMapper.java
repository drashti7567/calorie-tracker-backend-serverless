package com.drash.lambda.mapper;

import com.drash.common.DrashCalorieException;
import com.drash.lambda.model.response.EntryModel;
import com.drash.lambda.model.response.ImmutableEntryModel;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.domain.User;
import com.drash.persistence.repo.UserRepo;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class EntryModelMapper implements ModelMapper<Entry, EntryModel> {

    private final UserRepo userRepo;

    @Override
    public EntryModel map(final Entry source) {
        return ImmutableEntryModel.builder()
                .userEmail(source.getPk())
                .eatTime(source.getSk())
                .entryId(source.getGs1pk())
                .calories(source.getCalories())
                .foodName(source.getFoodName())
                .userName(this.getUserName(source.getPk()))
                .creationTimeStamp(source.getCreatedAt())
                .lastUpdatedTimestamp(source.getUpdatedAt())
                .build();
    }

    private String getUserName(String pk) {
        Optional<User> user = userRepo.get(pk, null);
        if(user.isEmpty()) throw new DrashCalorieException("This user is not registered in the system");
        return user.get().getName();
    }

}
