package com.objectway.async.shopfinder.parallel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.async.shopfinder.model.Shop;

public class ShopFinderParallel {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopFinderParallel.class.getName());
	
	private static List<Shop> shopList = Arrays.asList(
			new Shop("Amazon"), new Shop("ePrice"), new Shop("OlloStore"), new Shop("Drako"),
			new Shop("BPM Power"), new Shop("HWOnline"), new Shop("Monclick"), new Shop("Yeppon"),
			new Shop("NewEgg"));
	
	public static void main(String[] args) {
		
		// 2. Search for shops in parallel.
		long startParallel = System.currentTimeMillis();
		List<String> shopsParallel = findPricesParallel("AMD Ryzen Threadripper 3990WX");
		long durationParallel = System.currentTimeMillis() - startParallel;
		
		printList(shopsParallel);
		logger.info("Elapsed time for parallel computation: {} secs.", durationParallel / 1000f);
	}
	
	private static List<String> findPricesParallel(String product) {
		return shopList.parallelStream()
				.map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}

	private static void printList(List<String> list) {
		for (String s : list) {
			logger.info(s);
		}
	}
}
