package com.objectway.behavioural.ingenuous;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Apple;


/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Data Manipulation - Filtering list in an more compact way than before, but it's still TERRIBLE.
 * Problem: What's better? And what's still bad?
 */
public class DataFiltering_3 {
	
	private static final Logger logger = LoggerFactory.getLogger(DataFiltering_3.class.getName());

	public static void main(String[] args) {
		
		List<Apple> inventory = new ArrayList<>();
		
		// Some apples. 
		Apple apple1 = new Apple("red", 100);
		Apple apple2 = new Apple("green", 150);
	    Apple apple3 = new Apple("red", 180);
	    Apple apple4 = new Apple("yellow", 130);
	    
	    // Let's put them in our inventory.
	    inventory.add(apple1);
	    inventory.add(apple2);
	    inventory.add(apple3);
	    inventory.add(apple4);
	    
	    // Let's have fun with simple filtering.
	    List<Apple> redApples = filterApples(inventory, "red", 0, true);
	    List<Apple> yellowApples = filterApples(inventory, "yellow", 0, true);
	    List<Apple> heavyApples = filterApples(inventory, "", 130, false);
	    List<Apple> heavierApples = filterApples(inventory, "", 150, false);
	    
	    // Let's check the result printing it out to the screen.
	    printInventory(redApples);
	    printInventory(yellowApples);
	    printInventory(heavyApples);
	    printInventory(heavierApples);
	}
	
	/**
	 * Filter the inventory by the specified parameters.
	 * @param inventory - The original list of apples
	 * @param color - The color used to filter
	 * @param weight - The weight used to filter
	 * @param flag - If true we filter by color, if false we filter by weight
	 * @return List<Apple> - The resulting collection
	 */
	public static List<Apple> filterApples(List<Apple> inventory, String color, Integer weight, Boolean flag) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (flag && color.equalsIgnoreCase(apple.getColor()) ||
					!flag && apple.getWeight() > weight)
				result.add(apple);
		}
		
		return result;
	}
    
	/**
	 * Just prints out the inventory content.
	 * @param inventory - The inventory that we want to inspect
	 */
	public static void printInventory(List<Apple> inventory) {
	    logger.info("Inventory contains {} apples.", inventory.size());
		for (int i = 0; i < inventory.size(); i++) {
			Apple apple = inventory.get(i);
			logger.info("Apple {}: Color: {} Weight: {}", i, apple.getColor(), apple.getWeight());
		}
	}
}
