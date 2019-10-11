package com.objectway.collectors;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * The art of collect().
 * Reducing and summarizing is typically used when it's needed to reorganize
 * the stream's items into a collection or for combining all elements into a single result.
 * collect() is a form of reduction and a collector is the way we specify how to reduce or 
 * transform the stream in the final process. Collectors are available as static factory methods
 * in the Collectors class. All the predefined collectors are a specialized version for Collectors.reducing().
 * 
 * Topics covered:
 *  - Collectors.counting() 
 *  - Collectors.maxBy(Comparator<T>), Collectors.minBy(Comparator<T>) --> Optional<T>
 *  - Collectors.summingInt(ToIntFunction), Collectors.summingLong(ToLongFunction), Collectors.summingDouble(ToDoubleFunction)
 *  - Collectors.averagingInt(ToIntFunction), Collectors.averagingLong(ToLongFunction), Collectors.averagingDouble(ToDoubleFunction)
 *  - Collectors.summarizingInt(ToIntFunction), Collectors.summarizingLong(ToLongFunction), Collectors.summarizingDouble(ToDoubleFunction)
 *  - Collectors.joining()
 *  - Collectors.reducing(T, Function<T, R>, BinaryOperator<T>)
 *  - Collectors.reducing(BinaryOperator<T>)
 */
public class PredefinedCollectors {
	
	private static final Logger logger = LoggerFactory.getLogger(PredefinedCollectors.class.getName());

	public enum CaloricLevel {
		DIETETIC, NORMAL, FAT
	};

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		// You can count the elements of a stream.
		Long noOfElements = menu.stream().collect(Collectors.counting());
		logger.info("Number of elements: {}", noOfElements);
		
		// More easily written as:
		Long anotherNoOfElements = menu.stream().count();

		// Finding the max or the min for an element of a stream.
		Optional<Dish> max = menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
		Optional<Dish> min = menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories)));
		logger.info("Max calories: {}, Min calories: {}", max.get().getCalories(), min.get().getCalories());
		
		// Can we just count all calories in the dishes?
		Long totalCalories = menu.stream().collect(Collectors.summingLong((Dish d) -> d.getCalories()));
		logger.info("All dishes in the menu have {} total calories.", totalCalories);
		
		// And what are the average calories for all the dishes?
		double averageCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		logger.info("Average calories for the dishes: {}", averageCalories);
		
		// Ok, but what if I want to compute more than one of these stats all at once for the calories?
		IntSummaryStatistics stats = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		logger.info("Total entries: {}, Total calories: {}, Max: {}, Min: {}, Average: {}",
				stats.getCount(), stats.getSum(), stats.getMax(), stats.getMin(), stats.getAverage());
		
		// Can I join strings inside a stream? joining() concatena le stringhe generate da toString() dell'oggetto.
		Stream.Builder<String> builder = Stream.builder();
		Stream<String> strStream = builder.add("Ciao").add("from").add("Italy").build();
		String finalStr = strStream.collect(Collectors.joining(" "));
		logger.info("Concatenated String: {}", finalStr);
				
		/* reducing() is a generalization of all the predefined collectors seen so far.
		 * It takes a starting value (T), a Function<T, R> for extracting the elements for the reduction
		 * and a BinaryOperator<T> (BiFunction<T, T, T>) that performs the reduction.
		 */
		int summingCalories = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
		logger.info("Again, the total calories for the menu is {}", summingCalories);
		
		/* There is also a single argument version of reducing() that accept a BinaryOperator<T> only.
		 * In this version there is no starting value. It basically returns the same type that it manipulates,
		 * encapsulated in an Optional<T>. Finding the highest caloric dish is like:
		 */
		Optional<Dish> mostCaloricDish = menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
		logger.info("Most caloric dish: {} with {} calories.", mostCaloricDish.get().getName(), mostCaloricDish.get().getCalories());
		
		/*
		 * SO! What's the difference between .reduce() and Collectors.reducing()?
		 * .reduce() must combine elements but can also express operation that modify objects in place.
		 * (Not parallel friendly and can lead to concurrent modification exceptions. 
		 * Collectors.reducing() on the other hand guarantees its parallel friendliness.
		 */
	}

}
