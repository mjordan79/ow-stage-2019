package com.objectway.async.future;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTest {
	
	private static ExecutorService es = Executors.newCachedThreadPool();
	
	public static void main(String[] args) {
		
		
//		Map<Integer, Future<Double>> results = new HashMap<>();
//		for (int i = 0; i <= 7; i++) {
//			results.put(i, es.submit(new LongDoubleTask()));
//		}
//		
//		System.out.println("Trying to retrieve the Integer result...");
//		for (Map.Entry<Integer, Future<Double>> entry : results.entrySet()) {
//			Future<Double> fResult = entry.getValue();
//			double result = 0d;
//			
//			try {
//				result = fResult.get(10, TimeUnit.MINUTES);
//			} 
//			catch (InterruptedException | ExecutionException | TimeoutException e) {
//				System.out.println("Cannot get result " + entry.getKey() + " with exception: " + getMessage(e));
//			} 
//			finally {
//				System.out.println("Result " + entry.getKey() + " is: " + result);
//			}
//		}
	}
	
	public static String getMessage(Throwable t) {
		return Optional.ofNullable(t.getMessage()).orElse("Exception occurred. No message available.");
	}
}
