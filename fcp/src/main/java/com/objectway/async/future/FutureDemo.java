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
 * Limitations / not easy things to do with ExecutorServices:
 *    1) Not explicitly engineered for asynchronous programming (somewhat inbetween parallel and async, though).
 *    2) Combining two asynchronous tasks both when they're independent or one of them depends by another one.
 *    3) Waiting for the completion of all tasks defined by a set of Futures.
 *    4) Programmatically completing the Future task, for example by providing manually a result.
 *    5) Reacting to a Future completion through some sort of action (we must block at a certain point waiting for results).
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
		Optional<String> message = Optional.ofNullable(t.getMessage());
		return message.orElse("Exception occurred. No message available.");
	}
	
}
