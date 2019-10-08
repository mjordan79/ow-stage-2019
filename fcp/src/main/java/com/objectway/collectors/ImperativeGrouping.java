package com.objectway.collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;
import com.objectway.streams.exercises.helpers.MenuCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com
 * 
 * Grouping dishes by type.
 *
 */
public class ImperativeGrouping {
	
	private static final Logger logger = LoggerFactory.getLogger(ImperativeGrouping.class.getName());

	public static void main(String[] args) {
		
		List<Dish> menu = MenuCreator.getMenu(1500);
		
		// Key: Dish.Type, value: list of names.
		Map<Dish.Type, List<String>> dishesByType = new HashMap<>();
		
		for (Dish d : menu) {
			Dish.Type type = d.getType();
			List<String> names = null;
			if (dishesByType.containsKey(type)) {
				names = dishesByType.get(type);
			}
			else { // No key, no values.
				names = new ArrayList<>();
			}
			names.add(d.getName());
			dishesByType.put(type, names);
		}
		
		for (Map.Entry<Dish.Type, List<String>> entry : dishesByType.entrySet()) {
			List<String> names = dishesByType.get(entry.getKey());
			logger.info("Dish of type {} has {} entries.", entry.getKey(), names.size());
		}
	}
	
}
