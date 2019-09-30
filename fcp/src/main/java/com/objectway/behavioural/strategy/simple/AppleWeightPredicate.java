package com.objectway.behavioural.strategy.simple;

import com.objectway.behavioural.strategy.ApplePredicate;
import com.objectway.model.Apple;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Implements ApplePredicate for filtering by the apples's weight > 130.
 */
public class AppleWeightPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() > 130;
	}

}
