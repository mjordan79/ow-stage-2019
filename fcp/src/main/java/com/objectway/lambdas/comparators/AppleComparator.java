package com.objectway.lambdas.comparators;

import java.util.Comparator;

import com.objectway.model.Apple;

public class AppleComparator implements Comparator<Apple> {
	
	@Override
	public int compare(Apple o1, Apple o2) {
		return o1.getWeight().compareTo(o2.getWeight());
	}
	
}
