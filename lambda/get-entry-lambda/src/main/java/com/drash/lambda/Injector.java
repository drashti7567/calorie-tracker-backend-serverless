package com.drash.lambda;

/**
 * Helper class for accessing the injector component.
 *
 * @author drashti.dobariya
 */
public final class Injector {

    //Empty constructor to prevent outside instantiation
    private Injector() {}

    public static GetEntryLambdaComponent getInjector() {
        return DaggerGetEntryLambdaComponent
                .builder()
                .build();
    }
}
