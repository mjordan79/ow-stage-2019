package com.objectway.async.shopfinder.asynchronous;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.async.shopfinder.model.Shop;

public class ShopFinderAsynchronous {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopFinderAsynchronous.class.getName());
	
	private static List<Shop> shopList = Arrays.asList(
			new Shop("Amazon"), new Shop("ePrice"), new Shop("OlloStore"), new Shop("Drako"),
			new Shop("BPM Power"), new Shop("HWOnline"), new Shop("Monclick"), new Shop("Yeppon"),
			new Shop("NewEgg"));

	public static void main(String[] args) {
		
		// 3. Search for shops asynchronously
		long startAsync = System.currentTimeMillis();
		List<String> shopsAsync = findPricesAsync("AMD Ryzen Threadripper 3990WX");
		long durationAsync = System.currentTimeMillis() - startAsync;
		
		printList(shopsAsync);
		logger.info("Elapsed time for asynchronous computation: {} secs.", durationAsync / 1000f);
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
