package com.drash.lambda;

import com.drash.lambda.handler.UpdateEntryHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { UpdateEntryLambdaModule.class })
public interface UpdateEntryLambdaComponent {

    void inject(UpdateEntryHandler handler);
}
