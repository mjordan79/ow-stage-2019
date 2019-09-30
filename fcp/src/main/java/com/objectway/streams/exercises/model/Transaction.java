package com.objectway.streams.exercises.model;

import java.util.Objects;

public class Transaction {
	
	private final Trader trader;
	private final int year;
	private final int value;
	
	public Transaction(Trader trader, int year, int value) {
		this.trader = Objects.requireNonNull(trader);
		this.year = Objects.requireNonNull(year);
		this.value = Objects.requireNonNull(value);
	}

	public Trader getTrader() {
		return trader;
	}

	public int getYear() {
		return year;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(trader, value, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(trader, other.trader) && value == other.value && year == other.year;
	}

	@Override
	public String toString() {
		return "{" + this.trader + ", " +
				"year: " + this.year + ", " +
				"value: " + this.value + "}";
	}
	
}
