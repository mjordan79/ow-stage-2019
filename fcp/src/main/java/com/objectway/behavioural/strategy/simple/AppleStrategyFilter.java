package com.objectway.behavioural.strategy.simple;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.behavioural.Apple;
import com.objectway.behavioural.strategy.ApplePredicate;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Refactor previous ingenuous approach to filtering by using the Strategy Pattern,
 * implementing a predicate to test for conditions. 
 */
public class AppleStrategyFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(AppleStrategyFilter.class.getName());

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
	    
	    // We build our strategies (Strategy Pattern). One for the color and one for the weight.
	    ApplePredicate yellowApplesPredicate = new AppleColorPredicate();
	    ApplePredicate weightApplesPredicate = new AppleWeightPredicate();
	    
	    // Let's filter.
	    List<Apple> yellowApples = filterApples(inventory, yellowApplesPredicate);
	    List<Apple> heavyApples = filterApples(inventory, weightApplesPredicate);
	    
	    // Let's check the result printing it out to the screen.
	    printInventory(yellowApples);
	    printInventory(heavyApples);

	}
	
	/**
	 * @param inventory - The original collection to filter.
	 * @param predicate - The strategy to apply for the filtering.
	 * @return List<Apple> - The resulting collection
	 */
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate predicate) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (predicate.test(apple))
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
