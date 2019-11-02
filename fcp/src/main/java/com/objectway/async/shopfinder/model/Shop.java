package com.objectway.async.shopfinder.model;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shop {
	
	private static final Logger logger = LoggerFactory.getLogger(Shop.class.getName());
	
	private final String shopName;
	
	public Shop(String shopName) {
		this.shopName = shopName;
	}
	
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	private double calculatePrice(String product) {
		delay();
		return getRandomLong(1_000, 10_000) * (double)product.charAt(0) + (double)product.charAt(1);
	}

	private static void delay() {
		try {
			Thread.sleep(1_000);
		}
		catch(InterruptedException e) {
			logger.error("Execution Interrupted!");
			Thread.currentThread().interrupt();
		}
	}
	
	private static long getRandomLong(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public String getShopName() {
		return shopName;
	}
	
}
