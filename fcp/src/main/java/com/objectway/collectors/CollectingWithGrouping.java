package com.objectway.collectors;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
 * 
 * 
 */
public class CollectingWithGrouping {
	
	private static final Logger logger = LoggerFactory.getLogger(CollectingWithGrouping.class.getName());

	public enum CaloricLevel {
		DIETETIC, NORMAL, FAT
	};

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
		
		// Grouping by key and counting elements.
		Map<Dish.Type, Long> counter = menu.stream()
				.collect(Collectors.groupingBy((Dish d) -> d.getType(), Collectors.counting()));
		for (Entry<Dish.Type, Long> entry : counter.entrySet()) {
			Dish.Type key = entry.getKey();
			Long value = counter.get(key);
			logger.info("For key: {} there are {} dishes.", key, value);
		}

		// Finding the dish with max calories for every type.
		Map<Dish.Type, Optional<Dish>> mostCaloric = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		
		// Composing collectors: CollectingAndThen
		Map<Dish.Type, Dish> mostCaloricWithExtraction = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, 
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
		
		// Advanced grouping: generating keys computationally.
		Map<CaloricLevel, List<Dish>> groupingByCalories = menu.stream().collect(Collectors.groupingBy((Dish d) -> {
			if (d.getCalories() <= 400)
				return CaloricLevel.DIETETIC;
			else if (d.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}));

		for (Entry<CaloricLevel, List<Dish>> entry : groupingByCalories.entrySet()) {
			CaloricLevel key = entry.getKey();
			List<Dish> values = groupingByCalories.get(key);
			logger.info("For key: {} there are {} dishes.", key, values.size());
		}
			
		// Multigrouping.
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> multiGroup = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else
						return CaloricLevel.FAT;
				})));
		
		// Total calories of dishes grouped by type. Summarization.
		Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
		
		// mapping(), like groupingBy() but values are Sets instead of Lists.
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.mapping((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else 
						return CaloricLevel.FAT;
				}, Collectors.toSet())));
		
		// Mapping with toCollection
		Map<Dish.Type, HashSet<CaloricLevel>> caloricLevelsByTypeWithCollection = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.mapping((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else 
						return CaloricLevel.FAT;
				}, Collectors.toCollection(HashSet::new))));
		
	}

}
