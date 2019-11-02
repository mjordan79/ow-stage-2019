package com.objectway.async.shopfinder.sequential;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.async.shopfinder.model.Shop;

public class ShopFinderSequential {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopFinderSequential.class.getName());
	
	private static List<Shop> shopList = Arrays.asList(
			new Shop("Amazon"), new Shop("ePrice"), new Shop("OlloStore"), new Shop("Drako"),
			new Shop("BPM Power"), new Shop("HWOnline"), new Shop("Monclick"), new Shop("Yeppon"),
			new Shop("NewEgg"));

	public static void main(String[] args) {
		
		// 1. Search for shops sequentially
		long startSequential = System.currentTimeMillis();
		List<String> shopsSequential = findPricesSequential("AMD Ryzen Threadripper 3990WX");
		long durationSequential = System.currentTimeMillis() - startSequential;
		
		printList(shopsSequential);
		logger.info("Elapsed time for sequential computation: {} secs.", durationSequential / 1000f);
	}
	
	private static List<String> findPricesSequential(String product) {
		return shopList.stream()
			    .map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(product)))
			    .collect(Collectors.toList());
	}

	private static void printList(List<String> list) {
		for (String s : list) {
			logger.info(s);
		}
	}
}
