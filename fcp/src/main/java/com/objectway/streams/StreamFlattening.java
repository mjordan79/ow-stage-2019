package com.objectway.streams;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Describes principles and applications behind the flatMap() stream operation.
 * 
 * With map(), each application of the Lambda function may not only extract "simple" objects
 * from the original element but also more complex data structures. Think for example at the 
 * String.split() method: it takes a string for the input and returns an array of characters.
 * "Hello" would return ["H", "e", "l", "l", "o"] for the output.
 * A map() operation on a Stream of strings would return a Stream of arrays of characters,
 * better described as Stream<String[]>, whereas you would like to obtain a Stream<String>.
 * flatMap() does the exactly same thing of map(), with the additional task of "flattening"
 * the resulting stream.
 * 
 * We will use the Collectors.toList() static method to convert a Stream<T> into a List<T> 
 * More specifically, only the method, statically imported
 */
public class StreamFlattening {

	private static final Logger logger = LoggerFactory.getLogger(StreamFlattening.class.getName());
	
	public static void main(String[] args) {
		
		//List<Dish> menu = MenuCreator.getMenu(1500);
		String[] arraysOfWords = {
			"Goodbye",
			"cruel",
			"world!"
		};
		
		/*
		 * To use the Stream API on an array, we should convert it into a stream first.
		 * An array is a fundamental data structure provided by the Java language, doesn't implement
		 * any collection interface not it extends one. In fact, arrays are not part of the JCF.
		 */
		Stream<String> array = Arrays.stream(arraysOfWords);
		
		/*
		 * Let's see what happens to the stream when splitting with the traditional map() 
		 */
		List<Stream<String>> result = array.map(s -> s.split("")) // At this point we have a Stream<String[]>
			.map(Arrays::stream) // At this point we have a Stream<Stream<String>>
			.distinct()
			.collect(toList()); // It ends up to a List<Stream<String>>
		logger.info("Result from the map() operation: {}", result);
		
		/* flatMap() comes to the rescue. Remember that a stream is consumed, 
		 * so we've have to recreate it 
		 */
		Stream<String> anotherArray = Arrays.stream(arraysOfWords);
		List<String> anotherResult = anotherArray.map(s -> s.split("")) // Ok, Stream<String[]>
				.flatMap(Arrays::stream) // With map(): Stream<Stream<String>>, with flatMap(): Stream<String>
				.distinct()
				.collect(toList());
	    logger.info("Result from the flatMap() operation: {}", anotherResult);
		
		

	}

}
