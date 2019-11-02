package com.objectway.async.completablefuture.supplyasync;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopSupplyAsync {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopSupplyAsync.class.getName());
	
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		try {
			futurePrice = CompletableFuture.supplyAsync(() -> calculatePrice(product));
		} catch(Exception e) {
			futurePrice.completeExceptionally(e);
		}
		return futurePrice;
	}
	
	// Previous implementation using CompletableFuture<T> and not the supplyAsync() method.
//	public Future<Double> getPriceAsync(String product) {
//		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//		Thread t = new Thread(() -> {
//			try {
//				double price = calculatePrice(product);
//				futurePrice.complete(price);
//			}
//			catch(Exception e) {
//				futurePrice.completeExceptionally(e);
//			}
//		});
//		
//		t.start();
//		
//		return futurePrice;
//	}
	
	private double calculatePrice(String product) {
		delay();
		return getRandomLong(1_000, 10_000) * product.charAt(0) + product.charAt(1);
	}

	private static void delay() {
		try {
			Thread.sleep(getRandomLong(2_000, 10_000));
		}
		catch(InterruptedException e) {
			logger.error("Execution Interrupted!");
			Thread.currentThread().interrupt();
		}
	}
	
	private static long getRandomLong(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
