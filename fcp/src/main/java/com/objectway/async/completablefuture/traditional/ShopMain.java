package com.objectway.async.completablefuture.traditional;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopMain {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopMain.class.getName());

	public static void main(String[] args) {
		Shop shop = new Shop();
		long start = System.nanoTime();
		
		// getPriceAsync implements the logic with a CompletableFuture.
		Future<Double> futurePrice = shop.getPriceAsync("My favourite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		logger.info("Invocation returned after {} {}", invocationTime, "msecs");
		
		doSomethingElse();
		
		try {
			double price = futurePrice.get();
			logger.info("Price is {}", price);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		logger.info("Price returned after {} {}",retrievalTime, "msecs.");
	}
	
	public static void doSomethingElse() {
		try {
			logger.info("Something else...");
			Thread.sleep(1000L);
		}
		catch(InterruptedException e) {
			logger.error("Execution Interrupted!");
			Thread.currentThread().interrupt();
		}
	}

}
