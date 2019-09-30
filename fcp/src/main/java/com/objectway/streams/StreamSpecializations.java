package com.objectway.streams;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Describes specialized Stream versions for primitive types.
 * These kind of streams have much more useful methods for operating on primitive types
 * instead of a generic reduce() operation. They also prevent massive boxing operations.
 *  
 * @see java.util.stream.IntStream
 * @see java.util.stream.LongStream
 * @see java.util.stream.DoubleStream
 * 
 * @see java.util.OptionalInt
 * @see java.util.OptionalInt
 * @see java.util.OptionalDouble
 */
public class StreamSpecializations {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamSpecializations.class.getName());
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(15);
		
		/*
		 * Recap the reduce() operation for non-primitive objects.
		 */
		int totalCalories = menu.stream()
				.map(Dish::getCalories) // First we map(), to extract the objects we need (Stream<Integer>)
				.reduce(0, Integer::sum); // And then we reduce(), to compute the result we want.
		logger.info("MapReduce: The whole menu has {} total calories!", totalCalories);
		
		/*
		 * Previous code might have a problem for primitive types: tons of boxing (and computational time wasted).
		 * Also, wouldn't be great to have a sum() specialized method instead of a reduce() one?
		 * reduce() can't because it works on Stream<T> objects, that are generic. But Java has Stream specializations
		 * even for primitive types: IntStream, LongStream, DoubleStream.
		 * To convert a Stream<T> in one of those streams, we also have special map operations: 
		 * mapToInt, mapToLong, mapToDouble.
		 * These interfaces have also specialized methods for calculations that make the task of reducing easier.
		 */
		int totalCaloriesWithIntStream = menu.stream()
				.mapToInt(Dish::getCalories) // Instead of map(), takes a specialized IntStream version.
				.sum(); // A special reduce operation that computes the sum. IntStream has also min(), max() and average()
		logger.info("MapReduce with IntStream: The whole menu has {} total calories!", totalCaloriesWithIntStream);
		
		/*
		 * With the operation .boxed() we can convert back a specialized Stream into a generic Stream<T> one.
		 * Converting back to specialized streams is useful for numeric ranges, covered in the next lesson. 
		 */
		IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
		Stream<Integer> genericStream = intStream.boxed();
		
		/*
		 * The sum() operation for specialized streams is convenient because it provides 0 for the initial value
		 * of the reduction. But what about max() or min()? They returns an OptionalInt, OptionalLong or OptionalDouble.
		 */
		OptionalInt maxWithSpecializedStream = menu.stream()
				.mapToInt(Dish::getCalories)
				.max();
		maxWithSpecializedStream.orElse(1);
	}

}
