package com.objectway.behavioural.ingenuous;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Apple;


/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Data Manipulation - Filtering list in an ingenuous way, but better than before.
 * Problem: better than before, but still, what's the problem?
 */
public class DataFiltering_2 {
	
	private static final Logger logger = LoggerFactory.getLogger(DataFiltering_2.class.getName());

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
	    List<Apple> redApples = filterApplesByColor(inventory, "red");
	    List<Apple> yellowApples = filterApplesByColor(inventory, "yellow");
	    List<Apple> heavyApples = filterApplesByWeight(inventory, 130);
	    List<Apple> heavierApples = filterApplesByWeight(inventory, 150);
	    
	    // Let's check the result printing it out to the screen.
	    printInventory(redApples);
	    printInventory(yellowApples);
	    printInventory(heavyApples);
	    printInventory(heavierApples);
	}
	
	/**
	 * Filter the inventory by the specified color.
	 * @param inventory - The original list of apples
	 * @param color - The color used to filter
	 * @return List<Apple> - The resulting collection
	 */
	public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (color.equalsIgnoreCase(apple.getColor()))
				result.add(apple);
		}
		
		return result;
	}
	
	/**
	 * Filter the inventory by the specified weight.
	 * @param inventory - The original list of apples
	 * @param weight - The weight used to filter
	 * @return List<Apple> - The resulting collection
	 */
	public static List<Apple> filterApplesByWeight(List<Apple> inventory, Integer weight) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > weight) {
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
