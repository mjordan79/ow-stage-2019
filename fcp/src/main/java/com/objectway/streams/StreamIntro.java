package com.objectway.streams;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Features:
 *   1) Declarative. WHAT instead of HOW.
 *   2) Composable. Builder pattern style, operations are a chain on top of a stream.
 *   3) Parallelizable. No more threads or complicated APIs, Streams benefit from automatic parallelism.
 *   4) Pipelining. Most operations return a new Stream themself.
 *   5) Internal Iteration. Programmers don't have to control the iteration process with boilerplate code.
 *   6) Consumable. Streams are traversable exactly once.
 *   7) Two kind of operations: intermediate and terminal.
 *   
 * Intermediate operations supported by the Stream<T> interface (they all return a new Stream<T>):
 *   filter(Predicate<T>) : T -> boolean
 *   map(Function<T, R>) : T -> R
 *   limit
 *   sorted(Comparator<T>) : (T, T) -> int
 *   distinct
 * Terminal operations:
 *   forEach - Consumes each element of the stream applying a lambda to each element returning void.
 *   count - Count the number of elements of the stream, returning a long.
 *   collect - Reduces a Stream into a collection (List, Map or Integer).
 *   
 * What are the benefits you see compared to TraditionalProcessing.java?
 * @see com.objectway.streams.TraditionalProcessing
 */
public class StreamIntro {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamIntro.class.getName());
	
	public static void main(String[] args) {
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		List<String> topThree = menu.stream()
			.filter(d -> d.getCalories() < 400)
			.distinct()
			.sorted((o1, o2) -> o1.getCalories().compareTo(o2.getCalories()))
			.map(Dish::getName)
			.limit(3)
			.collect(toList());
		
		logger.info("Top 3 dishes with less calories: {}", topThree);
	}
			
}
