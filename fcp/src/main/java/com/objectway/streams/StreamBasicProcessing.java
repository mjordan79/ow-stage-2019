package com.objectway.streams;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Describes basic Stream operations.
 *   
 * We will use the Collectors.toList() static method to convert a Stream<T> into a List<T> 
 * More specifically, only the method, statically imported
 *
 */
public class StreamBasicProcessing {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamBasicProcessing.class.getName());
	
	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		/* 
		 * .filter(Predicate<T>) -> Stream<T> 
		 * Returns another Stream containing the matching objects from the lambda.
		 */
		{			
			List<Dish> lowCaloriesDishes = menu.stream()
					.filter(d -> d.getCalories() < 400)
					.collect(toList());
			logger.info("Dishes with calories under 400kcal: {}", lowCaloriesDishes);
		}
		
		/*
		 * .distinct() -> Stream<T>
		 * Return a new Stream<T> containing only unique objects. It filters out 
		 * objects that are equal to others. Comparison for equality depends by
		 * the equals() and hashCode() methods defined in the data model class.
		 */
		{
			List<Dish> uniqueDishes = menu.stream()
					.distinct()
					.collect(toList());
			logger.info("Unique dishes in the menu: {}", uniqueDishes);
		}
		
		/*
		 * .limit(long maxSize) -> Stream<T>
		 * It truncates the whole original stream and returns another stream
		 * containing only the first maxSize elements specified.
		 */
		{
			final long maxSize = 3;
			List<Dish> limitedDishes = menu.stream()
					.limit(maxSize)
					.collect(toList());
			logger.info("First {} dishes in the menu: {}", maxSize, limitedDishes);
		}
		
		/*
		 * .skip(long n) -> Stream<T>
		 * Complementary to limit(), it truncates the Stream for the FIRST n elements
		 * and return a new Stream containing all the remaining elements.
		 * If the stream has less elements than those specified, it returns an empty stream.
		 */
		{
			final long n = 3;
			List<Dish> skipDishes = menu.stream()
					.skip(n)
					.collect(toList());
			logger.info("Skipping {} dishes in the menu: {}", n, skipDishes);
		}
		
		/*
		 * .map(Function<T, R>) -> Stream<T>
		 * It takes a function as an argument and apply it to every element of the stream.
		 * It returns a new Stream<T> with the objects extracted from the function. The
		 * returned Stream type depends by the type of the extracted object.
		 */
		{
			List<String> dishNames = menu.stream()
					.map(Dish::getName)
					.collect(toList());
			logger.info("Dish names in the menu: {}", dishNames);
		}
		
		/*
		 * .count() -> long
		 * Terminal operation that returns the number of elements inside the stream.
		 */
		{
			logger.info("Number of elements in the menu: {}", 
					menu.stream().count());
		}
		
		/*
		 * .forEach(Consumer<T>) -> void
		 * The forEach() stream method is a terminal operation that executes an action 
		 * defined by the Consumer for each elements, without returning anything from them 
		 * (remember that Consumer<T> has a functional descriptor of void accept(T t).
		 * It is basically a "void map" operation. It doesn't respect the order of the 
		 * stream
		 */
		{
			final long maxSize = 3;
			menu.stream()
				.limit(maxSize)
				.forEach(d -> {
					logger.info("forEach: {}", d);
				});
		}
		
		/*
		 * .sorted(Comparator<T>) -> Stream<T> takes a lambda that respect the Comparator Functional Interface.
		 * As you can imagine, the comparator is used in the confrontation of elements for sorting
		 * purposes. It returns another Stream with the sorted elements.
		 */
		{
			final long maxSize = 3;
			List<Dish> sortedDishes = menu.stream()
					.limit(maxSize)
					.sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
					.collect(toList());
			logger.info("{} dishes sorted by name: {}", maxSize, sortedDishes);
		}
		
	}
			
}
