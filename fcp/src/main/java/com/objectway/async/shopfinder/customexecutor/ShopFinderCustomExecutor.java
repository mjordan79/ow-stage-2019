package com.objectway.async.shopfinder.customexecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.async.shopfinder.model.Shop;

public class ShopFinderCustomExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopFinderCustomExecutor.class.getName());
	
	private static List<Shop> shopList = Arrays.asList(
			new Shop("Amazon"), new Shop("ePrice"), new Shop("OlloStore"), new Shop("Drako"),
			new Shop("BPM Power"), new Shop("HWOnline"), new Shop("Monclick"), new Shop("Yeppon"),
			new Shop("NewEgg"));
	
	private static final Executor executor = Executors.newFixedThreadPool(Math.min(shopList.size(), 100), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	});

	public static void main(String[] args) {
		
		// 4. Search for shops asynchronously but using a custom Executor 
		long startAsyncWithExecutor = System.currentTimeMillis();
		List<String> shopsAsyncWithExecutor = findPricesAsyncWithCustomExecutor("AMD Ryzen Threadripper 3990WX");
		long durationAsyncWithExecutor = System.currentTimeMillis() - startAsyncWithExecutor;
				
		printList(shopsAsyncWithExecutor);
		logger.info("Elapsed time for asynchronous computation: {} secs.", durationAsyncWithExecutor / 1000f);
	}
	
	private static List<String> findPricesAsyncWithCustomExecutor(String product) {
		List<CompletableFuture<String>> cf = shopList.stream()
				.map(shop -> CompletableFuture.supplyAsync(
						() -> shop.getShopName() + " price is " + shop.getPrice(product), executor))
				.collect(Collectors.toList());

		return cf.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	private static void printList(List<String> list) {
		for (String s : list) {
			logger.info(s);
		}
	}
}
