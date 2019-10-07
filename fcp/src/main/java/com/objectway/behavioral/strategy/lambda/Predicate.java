package com.objectway.behavioral.strategy.lambda;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Refactors the ApplePredicate interface to a more generic one.
 * @param <T>
 */
public interface Predicate<T> {
	boolean test(T t);
}
