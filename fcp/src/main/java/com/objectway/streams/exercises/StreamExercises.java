package com.objectway.streams.exercises;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Transaction;
import com.objectway.streams.exercises.helpers.TradingCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Exercise:
 *   1) Find all transactions in 2011 and sort by value (small to high)
 *   2) What are all the unique cities where the traders work?
 *   3) Find all traders that work in Milan and sort them by name.
 *   4) Return a string of all traders' names sorted alphabetically.
 *   5) Are there any traders that work in Turin?
 *   6) Print all transactions' values for traders living in Syracuse.
 *   7) What is the highest value of all transactions?
 *   8) Find the transaction with the smallest value.
 */
public class StreamExercises {
	
	private static Logger logger = LoggerFactory.getLogger(StreamExercises.class.getName());
	
	public static void main(String[] args) {

		List<Transaction> transactions = TradingCreator.getTransactions(20);
		transactions.stream().findAny().ifPresent(t -> logger.info("Sample trader: {}", t));
		
		// Let's start.
	}

}
