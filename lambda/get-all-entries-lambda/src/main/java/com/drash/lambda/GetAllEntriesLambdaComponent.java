package com.drash.lambda;

import com.drash.lambda.handler.GetAllEntriesHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { GetAllEntriesLambdaModule.class })
public interface GetAllEntriesLambdaComponent {

    void inject(GetAllEntriesHandler handler);
}
