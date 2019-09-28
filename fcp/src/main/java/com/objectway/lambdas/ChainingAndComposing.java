package com.objectway.lambdas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.behavioural.Apple;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Several functional interfaces that are used to pass lambda expressions provides methods
 * that allow composition and chaining of functions. These are default methods 
 * for functional interfaces.
 * 
 * @see java.util.Comparator<T>
 * @see java.util.Predicate<T>
 * @see java.util.Function<T, R>
 */
public class ChainingAndComposing {
	
	private static final Logger logger = LoggerFactory.getLogger(ChainingAndComposing.class.getName());
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// As usual, we start we a filled data structure.
		List<Apple> apples = new ArrayList<>();
		
		// By the way, a trick to generate objects from constructor references.
		BiFunction<String, Integer, Apple> bf = Apple::new;
		for (int i = 0; i < 10; i++) {
			apples.add(bf.apply("red", getRandomInt()));
		}
		
		
		{
			// Comparator<T> - reversed() default method.
			Comparator<Apple> c = Comparator.comparing(Apple::getWeight).reversed();
			apples.sort(c);
			logger.info("Apples sorted by weight in reversed order:\n {}", apples);
			Collections.shuffle(apples);
		}
		
		{
			// Comparator<T> - chaining - When two elements are equal, we give a second comparator
			Comparator<Apple> c = Comparator.comparing(Apple::getWeight)
					.reversed()
					.thenComparing(Apple::getColor);
		}
		
		{
			// Predicate<T> - negate a Predicate
			Predicate<Apple> red = a -> a.getColor().equalsIgnoreCase("red");
			Predicate<Apple> notRed = red.negate();
			
			// Predicate<T> - or() and and() default methods.
			Predicate<Apple> or = red.or(notRed);
			Predicate<Apple> and = red.and(notRed);
		}
		
		{
			// Function<T, R> - compose() default method, g(f(x)) and andThen() chain which represents f(g(x))
			Function<Integer, Integer> f = x -> x + 1;
			Function<Integer, Integer> g = x -> x * 2;
			Function<Integer, Integer> h = f.compose(g); // f(g(x)) 
			Function<Integer, Integer> i = f.andThen(g); // g(f(x))
			logger.info("h = f.compose(g) for x = 1: {}", h.apply(1));
			logger.info("i = f.andThen(g) for x = 1: {}", i.apply(1));	
 		}

	}
	
	/**
	 * Helper function that returns a pseudorandom int in a range.
	 */
	private static int getRandomInt() {
		final int MIN = 60, MAX = 150;

		Random r = new Random();
		return r.nextInt((MAX - MIN) + 1) + MIN;
	}

}
