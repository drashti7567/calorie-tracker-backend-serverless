package com.drash.lambda;

import com.drash.lambda.handler.GetEntryHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { GetEntryLambdaModule.class })
public interface GetEntryLambdaComponent {

    void inject(GetEntryHandler handler);
}
