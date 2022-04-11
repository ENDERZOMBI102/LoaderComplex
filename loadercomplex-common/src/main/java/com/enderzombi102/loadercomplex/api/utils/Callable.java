package com.enderzombi102.loadercomplex.api.utils;

/**
 * Represents a function that returns a value and that can't throw an exception.
 *
 * This is an exception-less version of {@link java.util.concurrent.Callable}
 *
 * @param <T> return type of the callable
 */
@FunctionalInterface
public interface Callable<T> {
	T call();
}
