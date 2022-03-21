package com.drash.lambda;

import com.drash.aws.AwsCoreModule;
import com.drash.lambda.mapper.EntryModelMapper;
import com.drash.lambda.mapper.ModelMapper;
import com.drash.lambda.model.response.EntryModel;
import com.drash.persistence.PersistenceModule;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.repo.UserRepo;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = {
        AwsCoreModule.class,
        PersistenceModule.class,
        LambdaCommonModule.class
})
public class GetAllEntriesLambdaModule {

    @Provides
    @Singleton
    public ModelMapper<Entry, EntryModel> entryModelMapper(UserRepo userRepo ) {
        return new EntryModelMapper(userRepo);
    }
}
