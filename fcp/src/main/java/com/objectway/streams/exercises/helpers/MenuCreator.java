package com.objectway.streams.exercises.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.objectway.model.Dish;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * An helper class that contains static methods to initialize a menu 
 * with various data structures.
 */
public class MenuCreator {
	
	private static Dish.Type[] dishTypes = new Dish.Type[3];
	
	private static String[] dishNames = {
			"chicken",
			"salad",
			"strawberry",
			"pizza",
			"beef",
			"pork"
	};
	
	static {
		dishTypes[0] = Dish.Type.FISH;
		dishTypes[1] = Dish.Type.MEAT;
		dishTypes[2] = Dish.Type.OTHER;
	}
	
	private MenuCreator() {}
	
	public static List<Dish> getMenu(int howMuch) {
		List<Dish> menu = new ArrayList<>();
		for (int i = 0; i < howMuch; i++) {
			Boolean vegetarian = getRandom(0, 1) == 0 ? Boolean.FALSE : Boolean.TRUE;
			String dishName = dishNames[getRandom(0, 5)];
			Dish.Type type = dishTypes[getRandom(0, 2)];
			int calories = getRandom(1, 1500);
			Dish d = new Dish(dishName, vegetarian, calories, type);
			menu.add(d);
		}
		return menu;
	}
	
	private static int getRandom(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
