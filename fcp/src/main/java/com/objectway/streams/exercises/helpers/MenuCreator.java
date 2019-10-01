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
		for (int i = 0, j = 0; i < dishTypes.length; i++, j++)
			dishTypes[i] = Dish.Type.values()[j];
	}
	
	private MenuCreator() {}
	
	public static List<Dish> getMenu(int howMuch) {
		List<Dish> menu = new ArrayList<>();
		for (int i = 0; i < howMuch; i++) {
			Boolean vegetarian = getRandomInt(0, 1) == 0 ? Boolean.FALSE : Boolean.TRUE;
			String dishName = dishNames[getRandomInt(0, 5)];
			Dish.Type type = dishTypes[getRandomInt(0, 2)];
			int calories = getRandomInt(1, 1500);
			Dish d = new Dish(dishName, vegetarian, calories, type);
			menu.add(d);
		}
		return menu;
	}
	
	private static int getRandomInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
