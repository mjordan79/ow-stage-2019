package com.objectway.behavioural.strategy.lambda;

/**
 * Refactors the ApplePredicate interface to a more generic one.
 * @author Renato Perini <renato.perini@objectway.com>
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {
	boolean test(T t);
}
