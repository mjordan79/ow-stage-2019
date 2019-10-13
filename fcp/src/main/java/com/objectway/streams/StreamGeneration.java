package com.objectway.streams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 *   
 * Until now we've always created streams from collections through the stream() method.
 * But there are many ways to generate new streams.
 *   1) From values
 *   2) empty streams
 *   3) From arrays
 *   4) From files
 *   5) Infinite streams with statis methods Stream.iterate() and Stream.generate()
 * 
 */
public class StreamGeneration {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamGeneration.class.getName());

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		/*
		 * Generating streams from values with Stream.of() static method.
		 */
		Stream<String> languages = Stream.of("Java", "c", "c++", "c#", "JavaScript");
		languages.forEach(i ->
					logger.info("String: {}", i.toUpperCase()));
		
		/*
		 * You can create an empty Stream.
		 */
		Stream<Integer> empty = Stream.empty();
		
		/*
		 * You can create streams from arrays.
		 */
		int[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		IntStream numArrayStream = Arrays.stream(numArray); // Note that this creates a specialized stream.
		
		/*
		 * Java NIO uses streams in lots of its static methods for java.nio.file.Files.
		 * For computing the number of unique words in a file:
		 */
		long uniqueWords = 0;
		
		try (Stream<String> lines = Files.lines(Paths.get("src/main/java/com/objectway/streams/StreamReducing.java"), Charset.defaultCharset())) {
			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
						       .distinct()
						       .count();
		} catch (IOException e) {
			logger.error("Uh oh! An exception has been thrown: {}", e.getCause());
		}
		logger.info("Unique words in StreamReducing.java: {}", uniqueWords);
		
		/*
		 * Infinite streams with iterate()
		 * It takes a starting value and a lambda of type UnaryOperator<T> to generate the stream.
		 * Generally useful when a stream of sequential values is needed.
		 */
		Stream<Integer> uhph = Stream.iterate(1,  n -> n + 2).limit(4);
		List<Integer> list = uhph.collect(Collectors.toList());
		for (Integer i : list) {
			logger.info("i in list: {}", i);
		}
		
		int sum = Stream.iterate(0,  n -> n + 2)
			.limit(4)
			.reduce(0, (i1, i2) -> i1 + i2);
		logger.info("Sum from infinite stream: {}", sum);
		
		/*
		 * Infinite streams with generate()
		 * Unlike iterate(), generate() doesn't provide successive values computed from the previous
		 * result. generate() uses a Supplier<T> as a generator function to provide elements.
		 */
		Stream.generate(Math::random)
			.limit(10)
			.forEach(i -> logger.info("Generated number with generate(): {}", i));
	}

}
