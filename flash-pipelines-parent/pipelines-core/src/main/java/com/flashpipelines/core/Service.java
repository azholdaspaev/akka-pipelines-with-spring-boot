package com.flashpipelines.core;

import java.util.function.Function;

/**
 * Interface identifies any type of service.
 *
 * @param <T> any input parameter type
 * @param <R> any result parameter type
 */
public interface Service<T, R> extends Function<T, R> {

}
