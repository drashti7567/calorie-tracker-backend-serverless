package com.drash.lambda;

import com.drash.aws.AwsCoreModule;
import com.drash.persistence.PersistenceModule;
import dagger.Module;

@Module(includes = {
        AwsCoreModule.class,
        PersistenceModule.class,
        LambdaCommonModule.class
})
public class UpdateEntryLambdaModule {
}
