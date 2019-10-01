package com.objectway.collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Transaction;
import com.objectway.streams.exercises.helpers.TradingCreator;

/**
 * @author Renato Perini <renato.perini@objectway.com
 * 
 * Grouping transactions by cities.
 *
 */
public class ImperativeGrouping {
	
	private static final Logger logger = LoggerFactory.getLogger(ImperativeGrouping.class.getName());

	public static void main(String[] args) {
		
		List<Transaction> transactions = TradingCreator.getTransactions(1500);
		
		// Key: city, value: List of values
		Map<String, List<Integer>> transactionsByCities = new HashMap<>();
		
		for (Transaction t : transactions) {
			String city = t.getTrader().getCity();
			List<Integer> values = null;
			if (transactionsByCities.containsKey(city)) {
				 values = transactionsByCities.get(city);
			} else { // No key, no values.
				 values = new ArrayList<>();
			}
			values.add(t.getValue());
			transactionsByCities.put(city, values);
		}
		
		for (Map.Entry<String, List<Integer>> entry : transactionsByCities.entrySet()) {
			List<Integer> values = transactionsByCities.get(entry.getKey());
			logger.info("City of {} has {} values.", entry.getKey(), values.size());
		}
		
	}
}
