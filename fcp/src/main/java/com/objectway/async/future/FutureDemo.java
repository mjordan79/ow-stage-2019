package com.objectway.async.future;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Shows the practical usage of basic asynchronous programming through
 * an ExecutorService, Future<T> and Callable<T> objects. 
 *
 */
public class FutureDemo {
	
	private static final Logger logger = LoggerFactory.getLogger(FutureDemo.class.getName());
	
	public static void main(String[] args) {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		int availProcessors = Runtime.getRuntime().availableProcessors();
		logger.info("Running on {} processors.", availProcessors);
		
		Map<Integer, Future<Double>> results = new ConcurrentHashMap<>();
		
		for (int i = 0; i < availProcessors; i++) {
			Future<Double> fResult = es.submit(new LongDoubleTask());
			results.put(i, fResult);
		}
		
		results.forEach((key, value) -> {
			double result = 0d;
			
			try {
				result = value.get(20, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.info("Cannot get result {} with exception: {}", key, getMessage(e));
			} finally {
				logger.info("Result {} is: {}", key, result);
			}
		});
		
		es.shutdown();
	}
	
	public static String getMessage(Throwable t) {
		return Optional.ofNullable(t.getMessage()).orElse("Exception occurred. No message available.");
	}
	
}
