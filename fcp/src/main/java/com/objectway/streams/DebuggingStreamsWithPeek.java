package com.objectway.streams;

import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.Dish;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * If you want to inspect intermediate state of streams while manipulating them in some pipeline
 * of operations, you have to put some instructions to print out the values generated / extracted 
 * from the intermediate operation. Printing instructions are generally put into Consumer<T> methods.
 * This approach has a big limitation to the point of being unfeasable: they CONSUME the stream
 * and any subsequent operation will emit a IllegalStateException due to the consumed stream.
 * 
 * The peek() method of the Stream interface comes to help: it accepts a 
 *
 */
public class DebuggingStreamsWithPeek {
	
	private static final Logger logger = LoggerFactory.getLogger(DebuggingStreamsWithPeek.class.getName());

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// What's happen if I put an intermediate .forEach(System.out::println) after .mapToInt?
		double inspectingWithPeek = Stream.generate(() -> new Dish("Salad", true, new Random().nextInt(450), Dish.Type.OTHER))
				.limit(10)
				.peek(System.out::println)
				.mapToInt(Dish::getCalories)
				.peek(System.out::println)
				.filter(n -> n < 400)
				.peek(s -> System.out.println("After filter: " + s))
				.average()
				.getAsDouble();
		logger.info("Average calories are: {}", inspectingWithPeek);
	}
}
