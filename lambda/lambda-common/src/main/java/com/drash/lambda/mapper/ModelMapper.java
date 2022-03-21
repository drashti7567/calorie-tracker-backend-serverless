package com.drash.lambda.mapper;

/**
 * Object that maps an object to another object.
 *
 * @param <S> The runtime type of the source object
 * @param <D> The runtime type of the destination object
 * @author arjay,nacion
 */
public interface ModelMapper<S, D> {

    /**
     * Maps the given source object to a target object.
     *
     * @param source The source object to map
     * @return Resulting object after mapping the given source object
     * @throws ModelMappingException If anything goes wrong during mapping
     */
    D map(S source) throws ModelMappingException;
}
