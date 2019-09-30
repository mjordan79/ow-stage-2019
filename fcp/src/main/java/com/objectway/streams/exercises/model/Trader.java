package com.objectway.streams.exercises.model;

import java.util.Objects;

public class Trader {
	
	private final String name;
	private final String city;
	
	public Trader(String name, String city) {
		this.name = Objects.requireNonNull(name);
		this.city = Objects.requireNonNull(city);
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trader other = (Trader) obj;
		return Objects.equals(city, other.city) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Trader: " + this.name + " in " + this.city;
	}
	
}
