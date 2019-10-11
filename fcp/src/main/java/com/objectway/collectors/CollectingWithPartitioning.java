package com.objectway.collectors;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Partitioning is a special case of grouping. In fact the classification function Function<T, U> is
 * substituted by a Predicate<T>. This means that the resulting groupings may only be 2: those elements
 * which are classified as true by the Predicate and those who are classified false.
 * As a consequence, the returning type from this grouping is:
 * Map<Boolean, List<T>>
 * 
 * Partitioning is made through the collector Collectors.partitioningBy(Predicate<T>)
 * 
 */
public class CollectingWithPartitioning {
	
	private static final Logger logger = LoggerFactory.getLogger(CollectingWithPartitioning.class.getName());
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(1500);

		// Simple form of partitioning. Boolean grouping in nature.
		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));

		// A simple Map where the key can be true or false only.
		List<Dish> vegetarian = partitionedMenu.get(true);
		List<Dish> notVegetarian = partitionedMenu.get(false);

		// or using lambdas, the list can be filtered out from the stream with a Predicate and its negation.
		Predicate<Dish> p = Dish::isVegetarian;
		List<Dish> vegetarianWithLambda = vegetarian.stream().filter(p).collect(Collectors.toList());
		List<Dish> notVegetarianWithLambda = vegetarian.stream().filter(p.negate()).collect(Collectors.toList());
		
		/* Collectors.partitioningBy() has a second overloaded version that allows to take in input a collector
		 * as a second parameter, allowing more complex groupings.
		 */
        Map<Boolean, Dish> collect = menu.stream().collect( 
        		Collectors.partitioningBy(Dish::isVegetarian, 
        				Collectors.collectingAndThen(
        						Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)
        				));
        
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
