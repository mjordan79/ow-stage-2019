package com.objectway.streams;

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
	
	public static List<Dish> getMenu(int howMuch) {
		List<Dish> menu = new ArrayList<>();
		for (int i = 0; i < howMuch; i++) {
			Random r = new Random();
			Boolean vegetarian = getRandom(0, 1, r) == 0 ? Boolean.FALSE : Boolean.TRUE;
			String dishName = dishNames[getRandom(0, 5, r)];
			Dish.Type type = dishTypes[getRandom(0, 2, r)];
			int calories = getRandom(1, 1500, r);
			Dish d = new Dish(dishName, vegetarian, calories, type);
			menu.add(d);
		}
		return menu;
	}
	
	private static int getRandom(int min, int max, Random r) {
		return r.nextInt((max - min) + 1) + min;
	}
	
}
