package com.objectway.parallel;

import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Problem: Calculate the sum of the first n numbers.
 * Solutions with iterative sequential, functional sequential, functional parallel and optimized functional parallelism.
 * Compare timings for the various implementations. Is the parallel one always the fastest?
 */
public class SequentialOrParallelSum {

	private static final Logger logger = LoggerFactory.getLogger(SequentialOrParallelSum.class.getName());
	
	private static final long NUMBERS = 50_000_000L;

	public static void main(String[] args) {

		// Iterative sequential code timing.
		measurePerf(SequentialOrParallelSum::functionalSequentialSum, NUMBERS, "Iterative sequential");

		// Functional sequential code timing.
		measurePerf(SequentialOrParallelSum::iterativeSequentialSum, NUMBERS, "Functional Sequential");

		// Functional Parallel code timing.
		measurePerf(SequentialOrParallelSum::functionalParallelSum, NUMBERS, "Executing on " 
				+ Runtime.getRuntime().availableProcessors() + " processors. Functional Parallel");
	
		// Optimized Functional Parallel code timing.
		measurePerf(SequentialOrParallelSum::optimizedFunctionalParallelSum, NUMBERS, "Executing on "
				+ Runtime.getRuntime().availableProcessors() + " processors. Optimized Functional Parallel");
	}

	/**
	 * Classic Iterative sequential.
	 * @param n - The first n numbers to sum.
	 * @return - The computed sum.
	 */
	public static long iterativeSequentialSum(long n) {
		long result = 0;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}

	/***
	 * Functional sequential with generic stream iteration and a generating function.
	 */
	public static long functionalSequentialSum(long n) {
		return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
	}

	/**
	 * Functional parallel sum, internally uses fork / join framework. Number of
	 * threads: Runtime.getRuntime().availableProcessors() System property:
	 * System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
	 * "12");
	 * iterate() is hard to parallelize, it struggles to split the input in chunks
	 * because each iteration depends by the previous result. 
	 * Also iterate() here generates boxed objects that need to be unboxed to be processed.
	 * 
	 * @param n - number of numbers to generate (and to sum)
	 * @return - the sum of the generated numbers.
	 */
	public static long functionalParallelSum(long n) {
		return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
	}

	/**
	 * Optimized version of the previous version using a specialized stream without autoboxing
	 * and the rangeClosed() generation method. Additionally, the stream is reduced in parallel.
	 * LongStream works with primitive longs, so no wasted time in unboxing objects.
	 * @param n - The numbers to generate and sum.
	 * @return - The sum of the generated numbers.
	 */
	public static long optimizedFunctionalParallelSum(long n) {
		return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
	}
	
	/**
	 * Executes the given function 10 times and capture the fastest execution time.
	 */
	public static long measurePerf(LongUnaryOperator adder, long n, String msg) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			adder.applyAsLong(n);
			long end = System.currentTimeMillis();
			long duration = end - start;
			if (duration < fastest)
				fastest = duration;
		}
		logger.info("{}: {} ms.", msg, fastest);
		return fastest;
	}

}
