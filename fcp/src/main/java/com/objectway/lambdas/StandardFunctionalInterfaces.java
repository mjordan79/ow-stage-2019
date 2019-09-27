package com.objectway.lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Describes basic standard Java SE 8 Functional Interfaces.
 * 
 * @see java.util.function.Predicate<T>
 * @see java.util.function.Consumer<T>
 * @see java.util.function.Function<T, R>
 * @see java.util.function.Supplier<T>
 */
public class StandardFunctionalInterfaces {
	
	private static final Logger logger = LoggerFactory.getLogger(StandardFunctionalInterfaces.class.getName());

	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		/* Predicate<T> has a functional descriptor of: boolean test(T t)
		 * Useful when we have to represent a boolean expression against a value. 
		 */
		List<Integer> filteredNumbers = filter(numbers, i -> i % 2 == 0);
		logger.info("numbers filtered with a predicate and filter(): {}", filteredNumbers);
		
		/* Consumer<T> has a functional descriptor of void accept(T t)
		 * Useful when we have to access objects of type T without returning anything from it.
		 */
		forEach(Arrays.asList(1, 2, 3, 4), i -> logger.info("{}", i));
		
		/* Function<T, R> has a functional descriptor of R apply(T t)
		 * Useful when we have to process the object T and returning a result of type R.
		 */
		List<Integer> addedNumbers = process(Arrays.asList(1, 2, 3, 4, 5), i -> i + 1);
        logger.info("numbers processed with apply(): {}", addedNumbers); 
        
        /* Supplier<T> has a functional descriptor of T get()
         * Useful when we want to return objects or values.
         */
        List<Integer> initialization = initialize(() -> 0);
        logger.info("List initialized with get(): {}", initialization);
	}
	
	/**
	 * Implements a filtering method for lists using a Predicate<T> for the behaviour.
	 */
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	/**
	 * Implements a forEach() method for lists using a Consumer<T> for the behaviour.
	 */
	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T t : list) {
			c.accept(t);
		}
	}
	
	/**
	 * Implements a process() method for lists using a Function<T, R> for the behaviour.
	 * It sums each number of the list with 1 and return the respective value into the list.
	 */
	public static <T, R> List<R> process(List<T> list, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for (T t : list) {
			R r = f.apply(t);
			result.add(r);
		}
		return result;
	}
	
	/**
	 * Implements a initialize() method for lists using a Supplier<T> for the behaviour.
	 * It just puts a value for each slot of the returned list.
	 */
	public static <T> List<T> initialize(Supplier<T> s) {
		List<T> result = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			result.add(s.get());
		}
		return result;
	}

}
