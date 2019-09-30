package com.objectway.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Describes principal stream operations for Stream reduction.
 * Until now, stream operations have returned objects after matching a specific query
 * through some lamda expression. With stream reducing we can combine elements of a stream
 * to answer to more elaborated queries. Stream reducing reduces a stream to a single value,
 * after a computation on all elements of the stream. Reduction is more geared towards computation
 * rather than element extraction.
 * 
 * Operations made on elements require a starting value. Think for example at the operation of sum
 * on all elements of the stream. You generally start the sum with 0 and then, progressively, you
 * sum each value of the stream.
 * eg.
 *      int sum = 0; <-- initial value required by .reduce()
 *      for (int x : numbers) {
 *          sum += x;
 *      }
 * 
 * It's all about the .reduce(BinaryOperator<T>) stream operation.
 * Remember that BinaryOperator<T> extends BiFunction<T, T, T>, as such it provides the following
 * functional descriptor:
 * 
 * 	T apply(T t1, T t2) : (T, T) -> T
 * 
 * We also introduce the MapReduce pattern for computing values on non-primitive objects.
 *  
 * @see java.util.function.BinaryOperator<T>
 */
public class StreamReducing {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamReducing.class.getName());

	public static void main(String[] args) {

	    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		/* 
		 * We want to sum every calories in the menu. We need an initial value ( = 0) and
		 * the elements on which the operation occurrs, among the effective operation to compute.
		 */
		int sumReduce = numbers.stream()
				.reduce(0, (i1, i2) -> i1 + i2);
		logger.info("Sum with reduce(): {}", sumReduce);
		
		/*
		 * reduce() has an overloaded version without an initialization value.
		 * It returns an Optional<T> instead.
		 * Also, Integer has a sum() static method, so we can use a method reference.
		 */
		Optional<Integer> sumOverloaded = numbers.stream()
				.reduce(Integer::sum);
		sumOverloaded.ifPresent(i -> {
			logger.info("Sum with the overloaded reduce() and the Integer.sum() static method: {}", i);
		});
		
		/*
		 * What about the min and the max for a stream? Well, Integer.max() and Integer.min() come to an help.
		 * At each iteration, the max is computer for two numbers. This value will be used in subsequent computations.
		 * So when the reduce() operation processes the last element of the stream, you can be assured it's the real 
		 * maximum (or minimum) value.
		 */
		int max = numbers.stream()
				.reduce(0, Integer::max); // (i1, i2) -> Integer.max(i1, i2) or (i1, i2) -> i1 < i2 ? i2 : i1
		int min = numbers.stream()
				.reduce(0, Integer::min); // (i1, i2) -> Integer.min(i1, i2) or (i1, i2) -> i1 < i2 ? i1 : i2
		logger.info("Max: {} and Min: {}", max, min);
		
		/*
		 * Ok but we have used only primitive numbers! How can I calculate the sum of the calories of the whole menu?
		 * The menu is a List<Dish>, so summing Dishes might be a problem (which initialization value I give?)
		 * MapReduce pattern, famous in Big Data processing (Apache Hadoop and Apache Spark comes to mind).
		 */
		List<Dish> menu = MenuCreator.getMenu(15);
		
		int totalCalories = menu.stream()
				.map(Dish::getCalories) // First we map(), to extract the objects we need (Stream<Integer>)
				.reduce(0, Integer::sum); // And then we reduce(), to compute the result we want.
		logger.info("The whole menu has {} total calories!", totalCalories);
		
		/*
		 * Can I compute the number of elements into a Stream without using the count() operation but only reduce?
		 * Again, Map-Reduce pattern will help.
		 */
		int totalElements = menu.stream()
			.map(d -> 1) // A Function<Dish, 1> will return a Stream<Integer> where all elements are 1!!!
			.reduce(0, Integer::sum);
		logger.info("Total number of elements using the MapReduce pattern: {}", totalElements);
	}

}
