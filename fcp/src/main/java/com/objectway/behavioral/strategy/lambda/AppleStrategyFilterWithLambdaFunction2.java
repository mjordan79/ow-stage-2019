package com.objectway.behavioral.strategy.lambda;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Apple;


/**
 * @author Renato Perini <renato.perini@objectway.com>
 * @see com.objectway.behavioral.strategy.lambda.Predicate
 * Refactor previous example with a more generic type.
 */
public class AppleStrategyFilterWithLambdaFunction2 {
	
	private static final Logger logger = LoggerFactory.getLogger(AppleStrategyFilterWithLambdaFunction2.class.getName());

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
	    
	    // Let's filter.
	    List<Apple> yellowApples = filter(inventory, apple -> "yellow".equalsIgnoreCase(apple.getColor()));
	    
	    List<Apple>  heavyApples = filter(inventory, apple -> apple.getWeight() > 130);
	    
	    // Let's check the result printing it out to the screen.
	    printInventory(yellowApples);
	    printInventory(heavyApples);

	}
	
	/**
	 * @param list - The original collection to filter.
	 * @param p - The Lambda function to apply for each member of type T of the list.
	 * @return List<T> - The resulting collection
	 */
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e))
				result.add(e);
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
