package com.objectway.parallel;

import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Accumulator;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * A parallelized sum operation with side effect. The sum method modifies shared state from an object.
 * The state is accessed by the parallel code. Each execution returns a different result.
 * 
 * Hence, this parallel code is inherently broken, because sequential in nature. 
 * Don't use!!!
 */
public class ParallelSumWithSideEffects {

	private static final Logger logger = LoggerFactory.getLogger(ParallelSumWithSideEffects.class.getName());
	
	public static void main(String[] args) {
		// Let's execute it 10 times, just to see what happens.
		for (int i = 0; i < 10; i++) 
			logger.info("Returned value: {}", sideEffectSum(10_000));
	}
	
	public static long sideEffectSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n)
			.parallel()
			.forEach(accumulator::add);
		
		return accumulator.total;
	}

}
