package com.objectway.async.future;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * LongDoubleTask implements a fake long running task (through sleeping) that returns a double value.
 * This task is meant to be used into an ExecutorService and its result must
 * be retrieved using a Future<T> object.
 * 
 * @see java.util.concurrent.Callable
 */
public class LongDoubleTask implements Callable<Double> {
	
	@Override
	public Double call() throws Exception {
		Thread.sleep(getRandomInt(1, 15));
		return new Random().nextDouble();
	}
	
	private static long getRandomInt(int min, int max) {
		int number = new Random().nextInt((max - min) + 1) + min;
		return number * 1000;
	}
	
}
