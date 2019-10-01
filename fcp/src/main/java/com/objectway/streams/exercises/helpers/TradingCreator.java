package com.objectway.streams.exercises.helpers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.objectway.model.Trader;
import com.objectway.model.Transaction;

public class TradingCreator {
	
	private static String[] cities = {
			"Milan",
			"Rome",
			"Turin",
			"Florence",
			"Venice",
			"Syracuse"
	};
	
	private TradingCreator() {}
	
	/*
	 * Generates a List<Transaction> of arbitrary size.
	 */
	public static List<Transaction> getTransactions(int lenght) {
		List<Transaction> transactions = new ArrayList<>();
		for (int i = 0; i < lenght; i++) {
			Trader trader = new Trader(getRandomString(7), cities[getRandomInt(0, cities.length - 1)]);
			Transaction transaction = new Transaction(trader, getRandomInt(2000, 2019), getRandomInt(1000, 10000));
			transactions.add(transaction);
		}
		return transactions;
	}
	
	/*
	 * Generates a random integer x where min < x < max.
	 * It takes a random number generator
	 */
	private static int getRandomInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	/*
	 * Generates a random UTF-8 string of an arbitrary length.
	 */
	private static String getRandomString(int length) {
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		return new String(array, StandardCharsets.ISO_8859_1);
	}
	
}
