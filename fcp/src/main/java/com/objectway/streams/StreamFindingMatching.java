package com.objectway.streams;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Describes principal stream operations for finding and matching elements.
 * We will cover the following operations:
 *   .allMatch()
 *   .anyMatch()
 *   .noneMatch()
 *   .findFirst()
 *   .findAny()
 * 
 * @see java.util.Optional<T>
 */
public class StreamFindingMatching {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamFindingMatching.class.getName());

	public static void main(String[] args) {
		
		// We initialize our menu as usual.
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		/*
		 * boolean anyMatch(Predicate<T>) answer to the question "Is there at least one element selected
		 * by the given predicate?". By returning a boolean, anyMatch() classify as a terminal operation
		 * on the stream.
		 */
		if (menu.stream().anyMatch(d -> d.getName().equalsIgnoreCase("beef"))) {
			logger.info("The menu seems to contain some beef! Not properly a vegan menu, isn't it? :-)");
		}
		else 
			logger.info("No beef in the menu. Seems like we're in a vegan restaurant.");

		/*
		 * boolean allMatch(Predicate<T>) answer to the question "Does the predicate selects ALL elements
		 * in the stream?". Terminal operation, of course. 
		 */
		if (menu.stream().allMatch(d -> d.getCalories() == 400)) {
			logger.info("allMatch(): All dishes are 400 calories? What a flat menu!");
		}
		else
			logger.info("allMatch(): Obviously (and fortunately) not all dishes are 400 calories.");
			
		
		/*
		 * boolean noneMatch(Predicate<T>) answer to the question "Does the predicate selects NONE of the 
		 * elements in the stream?". The opposite of anyMatch(). Terminal operation. 
		 */
		if (menu.stream().noneMatch(d -> d.getCalories() == 400)) {
			logger.info("noneMatch(): We don't have elements in the stream selected by our predicate.");
		}
		else
			logger.info("noneMatch(): Some elements are covered by our predicate.");
		
		/*
		 * Optional<T> findAny() just returns an ARBITRARY element in the stream.
		 * Optional is a container wrapper class that may contain null values or the intended object.
		 * Used to mitigate the possibility of a NullPointerException (NPE).
		 */
		Optional<Dish> findAny = menu.stream().findAny();
		findAny.ifPresent(d -> {
			logger.info("findAny(): Whoa, element present with name {} and {} calories.", d.getName(), d.getCalories());
		});
		
		/*
		 * Optional<T> findFirst() just returns the first element of a stream.
		 * As findAny(), it returns an Optional<T>
		 * Terminal operation.
		 */
		Optional<Dish> findFirst = menu.stream()
				.filter(d -> d.isVegetarian()) // Only vegetables this evening, damn!
				.findFirst();
		findFirst.ifPresent(d -> {
			logger.info("Vegans can eat in our restaurant, today: we serve {} and {} calories.", d.getName(), d.getCalories());
		});
	}

}
