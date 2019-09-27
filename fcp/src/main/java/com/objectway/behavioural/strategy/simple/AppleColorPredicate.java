package com.objectway.behavioural.strategy.simple;

import com.objectway.behavioural.Apple;
import com.objectway.behavioural.strategy.ApplePredicate;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Implements ApplePredicate for filtering by the apples's yellow color.
 */
public class AppleColorPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return "yellow".equalsIgnoreCase(apple.getColor());
	}

}
