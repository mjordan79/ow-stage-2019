package com.objectway.behavioral.ingenuous;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Apple;


/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Data Manipulation - Filtering list in an ingenuous way.
 * What is the problem?
 */
public class DataFiltering_1 {
	
	private static final Logger logger = LoggerFactory.getLogger(DataFiltering_1.class.getName());

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
	    List<Apple> redApples = filterRedApples(inventory);
	    List<Apple> yellowApples = filterYellowApples(inventory);
	    List<Apple> heavyApples = filterHeavyApples(inventory);
	    List<Apple> heavierApples = filterHeavierApples(inventory);
	    
	    // Let's check the result printing it out to the screen.
	    printInventory(redApples);
	    printInventory(yellowApples);
	    printInventory(heavyApples); // > 130 grams
	    printInventory(heavierApples); // > 150 grams
	}
	
	/**
	 * Filter a list and returns a new list containing only red apples.
	 * @param inventory - The original inventory for apples.
	 * @return List<Apple> - A new inventory containing red apples.
	 */
	public static List<Apple> filterRedApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("red".equalsIgnoreCase(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	/**
	 * Filter a list and returns a new list containing only yellow apples.
	 * @param inventory - The original inventory for apples.
	 * @return List<Apple> - A new inventory containing yellow apples.
	 */
	public static List<Apple> filterYellowApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("yellow".equalsIgnoreCase(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	/**
	 * Filter a list and returns a new list containing apples that weight > 130 grams.
	 * @param inventory - The original inventory for apples.
	 * @return List<Apple> - The resulting inventory
	 */
	public static List<Apple> filterHeavyApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > 130) {
				result.add(apple);
			}
		}	
		return result;
	}
	
	/**
	 * Filter a list and returns a new list containing apples that weight > 150 grams.
	 * @param inventory - The original inventory for apples.
	 * @return List<Apple> - The resulting inventory
	 */
	public static List<Apple> filterHeavierApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > 150) {
				result.add(apple);
			}
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
