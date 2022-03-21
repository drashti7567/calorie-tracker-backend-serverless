package com.drash.lambda;

import com.drash.lambda.handler.CreateEntryHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { CreateEntryLambdaModule.class })
public interface CreateEntryLambdaComponent {

    void inject(CreateEntryHandler handler);
}
