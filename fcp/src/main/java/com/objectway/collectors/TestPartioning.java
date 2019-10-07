package com.objectway.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

public class TestPartioning {

	public static void main(String[] args) {
		List<Dish> menu = MenuCreator.getMenu(1500);

		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));

		List<Dish> vegetarian = partitionedMenu.get(true);
		List<Dish> notVegetarian = partitionedMenu.get(false);

		// or
		List<Dish> vegetarianWithLambda = vegetarian.stream().filter(Dish::isVegetarian).collect(Collectors.toList());

		partitionPrimes(Long.MAX_VALUE);

	}

	public static Map<Boolean, List<Long>> partitionPrimes(long n) {
		return LongStream.rangeClosed(2, n).boxed().collect(Collectors.partitioningBy(candidate -> {
			boolean test = isPrime(candidate);
			if (test)
				System.out.println("Got it: " + candidate);
			return test;
		}));
	}

	public static boolean isPrime(long candidate) {
		return LongStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
	}

}
