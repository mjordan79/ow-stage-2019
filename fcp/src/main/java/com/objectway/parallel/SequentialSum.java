package com.objectway.parallel;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequentialSum {

	private static final Logger logger = LoggerFactory.getLogger(SequentialSum.class.getName());
	
	private final static long NUMBERS = 1_000_000L;

	public static void main(String[] args) {

		// Iterative sequential code timing.
		long fsstart = System.nanoTime();
		long fss = functionalSequentialSum(NUMBERS);
		long fssend = System.nanoTime();
		logger.info("Iterative sequential: {} ns.", fssend - fsstart);

		// Functional sequential code timing.
		long isstart = System.nanoTime();
		long iss = iterativeSequentialSum(NUMBERS);
		long isend = System.nanoTime();
		logger.info("Functional Sequential: {} ns.", isend - isstart);

		// Functional Parallel code timing.
		long fpstart = System.nanoTime();
		long fps = functionalParallelSum(NUMBERS);
		long fpend = System.nanoTime();
		logger.info("Executing on {} processors. Functional Parallel: {} ns.",
				Runtime.getRuntime().availableProcessors(), fpend - fpstart);

		// Speedy Functional Parallel code timing.
		long sfpstart = System.nanoTime();
		long sfps = speedyFunctionalParallelSum(NUMBERS);
		long sfpend = System.nanoTime();
		logger.info("Executing on {} processors. Speedy Functional Parallel: {} ns.",
				Runtime.getRuntime().availableProcessors(), sfpend - sfpstart);
	}

	// Iterative sequential.
	public static long iterativeSequentialSum(long n) {
		long result = 0;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}

	// Functioanal sequential.
	public static long functionalSequentialSum(long n) {
		return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
	}

	/**
	 * Functional parallel sum, internally uses fork / join framework. Number of
	 * threads: Runtime.getRuntime().availableProcessors() System property:
	 * System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
	 * "12");
	 * 
	 * @param n - number of numbers to generate (and to sum)
	 * @return - the sum of the generated numbers.
	 */
	public static long functionalParallelSum(long n) {
		return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
	}

	public static long speedyFunctionalParallelSum(long n) {
		return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
	}

}
