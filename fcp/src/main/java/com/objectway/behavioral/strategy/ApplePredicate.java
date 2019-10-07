package com.objectway.behavioral.strategy;

import com.objectway.model.Apple;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Filtering apples consists of returning a Boolean value
 * according to the verification of some attributes. This is called a predicate.
 * A predicate check for the condition and returns true if it's verified, false otherwise.
 * This allows to define a family of algorithms, each algorithm is a strategy.
 */
@FunctionalInterface
public interface ApplePredicate {
	boolean test(Apple apple);
}
