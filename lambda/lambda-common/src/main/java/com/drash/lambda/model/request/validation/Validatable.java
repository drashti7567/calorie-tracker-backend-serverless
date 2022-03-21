package com.drash.lambda.model.request.validation;

import am.ik.yavi.core.Validator;

public interface Validatable<E> {

    Validator<E> validator();
}
