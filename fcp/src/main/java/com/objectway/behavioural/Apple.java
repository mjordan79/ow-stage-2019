package com.objectway.behavioural;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * This data model bean formalizes a really complex description for an apple.
 */
public class Apple implements Serializable {
	
	private static final long serialVersionUID = 3489660711170492820L;
	
	private String color;
	private Integer weight;
	private Double price;

	// Constructors
	public Apple() {}
	
	public Apple(String color) {
		this.color = Objects.requireNonNull(color);
	}
	
	public Apple(Integer weight) {
		this.weight = Objects.requireNonNull(weight);
	}
	
	public Apple(String color, Integer weight) {
		this.color = Objects.requireNonNull(color);
		this.weight = Objects.requireNonNull(weight);
	}
	
	public Apple(String color, Integer weight, Double price) {
		this.color = Objects.requireNonNull(color);
		this.weight = Objects.requireNonNull(weight);
		this.price = price;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[color=")
		       .append(color)
		       .append(" weight=")
		       .append(weight)
			   .append("]");

		return builder.toString();
	}
	
}
