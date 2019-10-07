package com.objectway.collectors;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

public class TestGrouping {

	public enum CaloricLevel {
		DIETETIC, NORMAL, FAT
	};

	public static void main(String[] args) {
		List<Dish> menu = MenuCreator.getMenu(1500);

		Map<Dish.Type, Long> counter = menu.stream()
				.collect(Collectors.groupingBy((Dish d) -> d.getType(), Collectors.counting()));

		Map<Dish.Type, Optional<Dish>> mostCaloric = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		
		// CollectingAndThen
		Map<Dish.Type, Dish> mostCaloricWithExtraction = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, 
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
		
		
		Map<CaloricLevel, List<Dish>> groupingByCalories = menu.stream().collect(Collectors.groupingBy((Dish d) -> {
			if (d.getCalories() <= 400)
				return CaloricLevel.DIETETIC;
			else if (d.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}));

		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> multiGroup = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy((Dish d) -> {
					if (d.getCalories() <= 400)
						return CaloricLevel.DIETETIC;
					else if (d.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else
						return CaloricLevel.FAT;
				})));
		
		Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
		
		// Mapping
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
