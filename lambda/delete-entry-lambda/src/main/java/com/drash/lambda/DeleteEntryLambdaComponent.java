package com.drash.lambda;

import com.drash.lambda.handler.DeleteEntryHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { DeleteEntryLambdaModule.class })
public interface DeleteEntryLambdaComponent {

    void inject(DeleteEntryHandler handler);
}
