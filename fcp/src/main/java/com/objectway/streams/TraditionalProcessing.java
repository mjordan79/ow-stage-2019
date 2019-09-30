package com.objectway.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Generates a list of dishes, filter the list for those dishes that are less than 400kcal,
 * removes duplicated dishes, sort the list by calories, extract names from the objects and
 * finally presents the user with the top three less energetic dishes.
 * 
 * Try to describe the structure of the program. How is it built? What are its characteristics?
 * Enumerate the strong points of this code and / or its problems.
 */
public class TraditionalProcessing {
	
	private static final Logger logger = LoggerFactory.getLogger(TraditionalProcessing.class.getName());
	
	public static void main(String[] args) {
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		// Manual filtering dishes for calories < 400 kcal
		List<Dish> lowCaloriesDishes = new ArrayList<>();
		for (Dish d : menu) {
			if (d.getCalories() < 400) {
				logger.info("Found a low calories dish: {}", d);
				lowCaloriesDishes.add(d);
			}
		}
		
		// Removing duplicates.
		Set<Dish> removedDuplicates = new HashSet<>();
		boolean removed = removedDuplicates.addAll(lowCaloriesDishes);
		List<Dish> distinctDishes = new ArrayList<>(removedDuplicates);
		if (removed) {
			logger.info("Duplicated elements removed: {}", lowCaloriesDishes.size() - distinctDishes.size());
		}
		
		// Sorting by calories.
		Collections.sort(distinctDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return Integer.compare(o1.getCalories(), o2.getCalories());
			}
		});
				
		// Extract names for those low calories dishes.
		List<String> lowCaloriesDishesNames = new ArrayList<>();
		for (Dish d : distinctDishes) {
			lowCaloriesDishesNames.add(d.getName());
		}
		
		// Get and store the first 3 dishes with less calories.
		List<String> topThree = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			topThree.add(lowCaloriesDishesNames.get(i));
		}
		logger.info("Top 3 dishes with less calories: {}", topThree);
	}

}
