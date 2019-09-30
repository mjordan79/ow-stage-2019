package com.objectway.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Dealing with numbers means coping with numeric ranges sometimes.
 * For IntStream and LongStream Java can generate numeric ranges with the operations:
 * .range() and .rangeClosed()
 * .range() is an exclusive range, while rangeClosed() is an inclusive range.
 * These are static methods for the IntStream and LongStream classes.
 * 
 * @see java.util.stream.IntStream
 * @see java.util.stream.LongStream
 */
public class StreamRanges {

	private static final Logger logger = LoggerFactory.getLogger(StreamRanges.class.getName());
	
	public static void main(String[] args) {
		
		/*
		 * Let's generate an exclusive range from 1 to 9
		 */
		IntStream evenNumbers = IntStream.range(1, 10)
				.filter(i -> i % 2 == 0);
		List<Integer> list = evenNumbers.boxed().collect(Collectors.toList());
		logger.info("Even numbers in the range with range(): {}", list);
		
		/*
		 * Let's generate an inclusive range, from 1 to 10
		 */
		IntStream evenNumbers2 = IntStream.rangeClosed(1, 10)
				.filter(i -> i % 2 == 0);
		List<Integer> list2 = evenNumbers2.boxed().collect(Collectors.toList());
		logger.info("Even numbers in the range with rangeClosed(): {}", list2);
	}

}
