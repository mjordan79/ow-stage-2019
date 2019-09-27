package com.objectway.behavioural;

import java.io.Serializable;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * This data model bean formalizes a really complex description for an apple.
 */
public class Apple implements Serializable {
	
	private static final long serialVersionUID = 3489660711170492820L;
	
	private String color;
	private Integer weight;

	// Constructor
	public Apple(String color, Integer weight) {
		this.color = color;
		this.weight = weight;
	}

	// Getter and Setter methods.
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
