package com.objectway.collectors;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.collectors.PredefinedCollectors.CaloricLevel;
import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Grouping is an advanced operation for reorganizing data in a stream, often transforming it
 * in an another collection, suitable for containing the grouping.
 * 
 * Generally speaking, the resulting collection is a Map<K, V> or a Map<K, Map<U, V>>
 * Collectors seen in this module:
 * 
 * Collectors.groupingBy(Function<T>)
 * Collectors.groupingBy(Function<T>, Collector)
 * Collectors.collectingAndThen()
 * Collectors.mapping()
 * Collectors.toSet()
 * Collectors.toCollection()
 */
public class CollectingWithGrouping {

	private static final Logger logger = LoggerFactory.getLogger(CollectingWithGrouping.class.getName());

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		// Simple form of grouping. The Function<T> is called the classification function. It represents the key. */
		Map<Dish.Type, List<Dish>> simpleGrouping = menu.stream().collect(Collectors.groupingBy(Dish::getType));
		
		// Advanced grouping: generating keys computationally.
		Map<CaloricLevel, List<Dish>> groupingByCalories = menu.stream().collect(Collectors.groupingBy((Dish d) -> {
			if (d.getCalories() <= 400)
				return CaloricLevel.DIETETIC;
			else if (d.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}));

		for (Map.Entry<CaloricLevel, List<Dish>> entry : groupingByCalories.entrySet()) {
			CaloricLevel key = entry.getKey();
			List<Dish> values = groupingByCalories.get(key);
			logger.info("For key: {} there are {} dishes.", key, values.size());
		}
		
		// Multilevel grouping. A groupingBy() that takes a classification function and another groupingBy().
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> multiGroup = menu.stream()
				.limit(10)
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else
						return CaloricLevel.FAT;
				})));
		
		 Iterator<Map.Entry<Dish.Type, Map<CaloricLevel, List<Dish>>>> outIt = multiGroup.entrySet().iterator();
		 while (outIt.hasNext()) {
			 Map.Entry<Dish.Type, Map<CaloricLevel, List<Dish>>> outEntry = outIt.next();
			 Dish.Type outKey = outEntry.getKey();
			 Map<CaloricLevel, List<Dish>> outValue = multiGroup.get(outKey);
			 Iterator<Map.Entry<CaloricLevel, List<Dish>>> innerIt = outValue.entrySet().iterator();
			 while (innerIt.hasNext()) {
				 Map.Entry<CaloricLevel, List<Dish>> innerEntry = innerIt.next();
				 CaloricLevel innerKey = innerEntry.getKey();
				 List<Dish> dishes = outValue.get(innerKey);
				 for (Dish d : dishes) {
					 logger.info("For type {} there is an entry containing key {} and value {}", outKey, innerKey, d.getName());
				 }
			 }
		 }
		
		// The second collector doesn't have to be a groupingBy(). Grouping by key and counting elements. 
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

		/* Composing collectors: CollectingAndThen(). This is a collector that accepts 2 parameters:
		 * A collector for the grouping and a Function for extracting the final value.
		 */
		Map<Dish.Type, Dish> mostCaloricWithExtraction = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(
						Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

		// Total calories of dishes grouped by type. Summarization. summingInt is a collector that reduces to an integer.
		Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));

		/* mapping() is a collector that transforms the data structure in another data structure by accumulating
		 * elements in it.
		 */
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.mapping((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else
						return CaloricLevel.FAT;
				}, Collectors.toSet())));
        
		for (Map.Entry<Dish.Type, Set<CaloricLevel>> entry : caloricLevelsByType.entrySet()) {
			Set<CaloricLevel> calories = caloricLevelsByType.get(entry.getKey());
			for (CaloricLevel cl : calories) {
				logger.info("For key {} we have elements {} in the Set.", entry.getKey(), cl);
			}
		}
		
		// Mapping with toCollection
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByTypeWithCollection = 
				menu.stream()
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
