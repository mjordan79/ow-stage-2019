package com.objectway.async.shopfinder.sequential;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopFinder {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopFinder.class.getName());
	
	private static List<Shop> shopList = Arrays.asList(
			new Shop("Amazon"), new Shop("ePrice"), new Shop("OlloStore"), new Shop("Drako"),
			new Shop("BPM Power"), new Shop("HWOnline"), new Shop("Monclick"), new Shop("Yeppon"));
	
	public static void main(String[] args) {
		
		// Search for shops sequentially
		long startSequential = System.currentTimeMillis();
		List<String> shopsSequential = findPricesSequential("AMD Ryzen Threadripper 3990WX");
		long durationSequential = System.currentTimeMillis() - startSequential;
		
		printList(shopsSequential);
		logger.info("Elapsed time for sequential computation: {} secs.", durationSequential / 1000f);
		
		// Search for shops in parallel.
		long startParallel = System.currentTimeMillis();
		List<String> shopsParallel = findPricesParallel("AMD Ryzen Threadripper 3990WX");
		long durationParallel = System.currentTimeMillis() - startParallel;
		
		printList(shopsParallel);
		logger.info("Elapsed time for parallel computation: {} secs.", durationParallel / 1000f);
		
		// Search for shops asynchronously
		long startAsync = System.currentTimeMillis();
		List<String> shopsAsync = findPricesAsync("AMD Ryzen Threadripper 3990WX");
		long durationAsync = System.currentTimeMillis() - startAsync;
		
		printList(shopsAsync);
		logger.info("Elapsed time for asynchronous computation: {} secs.", durationAsync / 1000f);
	}
	
	private static List<String> findPricesSequential(String product) {
		return shopList.stream()
			    .map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(product)))
			    .collect(Collectors.toList());
	}
	
	private static List<String> findPricesParallel(String product) {
		return shopList.parallelStream()
				.map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}
	
	private static List<String> findPricesAsync(String product) {
		List<CompletableFuture<String>> cf = shopList.stream()
				.map(shop -> CompletableFuture.supplyAsync(
						() -> shop.getShopName() + " price is " + shop.getPrice(product)))
				.collect(Collectors.toList());
		
		/* 
		 * Splitting the stream operations in two sequences. Streams are lazy, this avoid processing
		 * asynchronous stuff in a sequential synchronous model. In fact join() are the same thing of
		 * get() for the Future, except that it doesn't throw a checked exception. This avoid waiting
		 * for the result before scheduling other tasks.
		 */
		return cf.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	private static void printList(List<String> list) {
		for (String s : list) {
			logger.info(s);
		}
	}
}
