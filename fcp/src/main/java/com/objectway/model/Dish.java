package com.objectway.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * This is a data model class and it represents a dish in a restaurant's menu.
 * It has many fields describing the dish name, its category, calories and type. 
 */
public class Dish implements Serializable {

	private static final long serialVersionUID = -875903094531411356L;
	
	public enum Type {
		MEAT,
		FISH,
		OTHER
	}
	
	private String name;
	private boolean vegetarian;
	private Integer calories;
	private Type type;
	
	// Constructors.
	public Dish(String name, boolean vegetarian, Integer calories, Type type) {
		this.name = Objects.requireNonNull(name);
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = Objects.requireNonNull(type);
	}
	
	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(calories, name, type, vegetarian);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		return calories == other.calories && Objects.equals(name, other.name) && type == other.type
				&& vegetarian == other.vegetarian;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
