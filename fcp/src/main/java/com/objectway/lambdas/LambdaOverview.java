package com.objectway.lambdas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * @see com.objectway.behavioural.strategy.lambda.Predicate
 * Shows some examples of Lambda Expressions.
 */
public class LambdaOverview {

	private static final Logger logger = LoggerFactory.getLogger(LambdaOverview.class.getName());
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
		logger.info("Numbers: {}", numbers);
		
		/* The method Collections.sort() has a signature:
		 * static <T> void sort(List<T> list, Comparator<? super T> c)
		 * Comparator<? super T> is annotated with @FunctionalInterface.
		 * The abstract method that makes this interface functional is:
		 * int compare(T o1, T o2)
		 */
		Comparator<Integer> sorter = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
		
		Collections.sort(numbers, sorter);
		
		/* Converting an anonymous inner class to a Lambda Expression it's needed to analyze
		 * the implementation of the abstract method. The method parameters corresponds to the 
		 * parameters of the Lambda Expression. The return type of the method is the return type
		 * of the Lambda.
		 * So:
		 * int compare(Integer o1, Integer o2)
		 * Becomes:
		 * (Integer o1, Integer o2) -> o1.compareTo(o2)
		 * Or in a more contract form (type inference):
		 * (o1, o2) -> o1.compareTo(o2)
		 * 
		 * Note: the signature of the abstract method of a functional interface is also called
		 * function descriptor, because it's what we use to describe a lambda effectively.
		 */
		
		// So, we can translate this method call using an anonymous inner class
		Collections.sort(numbers, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		
		// to the more concise form using a Lambda Expression:
		Collections.sort(numbers, (o1, o2) -> o1.compareTo(o2));
		
		// A lambda can also be assigned to a variable, obviously:
		Comparator<Integer> comp = (o1, o2) -> o1.compareTo(o2);
		Collections.sort(numbers, comp);
		
		logger.info("Numbers: {}", numbers);
		
		/* Way BETTER. From 6 lines of code to a one-liner!!! 
		 * In other words: A lambda expression allows to write the implementation of the abstract method
		 * of a functional interface directly inline and treat the whole expression as an instance of a functional interface.
		 */
		
		
		/* QUESTION: How the previous Predicate<T> interface we've written can be annotated? */
	}

}
