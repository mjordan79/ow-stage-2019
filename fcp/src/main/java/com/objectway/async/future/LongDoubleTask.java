package com.objectway.async.future;

import java.util.concurrent.Callable;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * LongDoubleTask implements a long running task that returns a double value.
 * This task is meant to be used into an ExecutorService and its result must
 * be retrieved using a Future<T> object.
 * 
 * @see java.util.concurrent.Callable
 */
public class LongDoubleTask implements Callable<Double> {
	@Override
	public Double call() throws Exception {
		double result = 0d;
		for (long i = 0; i < Long.MAX_VALUE; i++)
			result += 0.00000000001;
		return result;
	}
}
