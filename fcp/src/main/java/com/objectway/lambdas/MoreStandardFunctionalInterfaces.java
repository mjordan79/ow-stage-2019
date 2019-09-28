package com.objectway.lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Describes most used specialized Functional Interfaces.
 * 
 * @see java.util.function.UnaryOperator<T> [ T -> T ]
 * @see java.util.function.BinaryOperator<T> [ (T, T) -> T ]
 * @see java.util.function.BiPredicate<L, R> [ (L, R) -> boolean ]
 * @see java.util.function.BiConsumer<T, U> [ (T, U) -> void ]
 * @see java.util.function.BiFunction<T, U, R> [ (T, U, R) -> R ]
 */
public class MoreStandardFunctionalInterfaces {
	
	private static final Logger logger = LoggerFactory.getLogger(MoreStandardFunctionalInterfaces.class.getName());
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		{
			/* UnaryOperator<T> is a specialization of Function<T, R>
			 * Use UnaryOperator<T> instead of Function<T, T>
			 * fd: int 
			 */
			UnaryOperator<Integer> uo = (Integer i) -> i * i;
			List<Integer> result = applyUnary(numbers, uo);
			logger.info("UnaryOperator<Integer>: {}", result);
		}
		
		{
			/* BinaryOperator<T> specializes BiFunction<T, T, T> */
			BinaryOperator<Integer> bo = (i, j) -> i * j;
			List<Integer> result = applyBinary(numbers, bo);
			logger.info("BinaryOperator<Integer>: {}", result);
		}
	}
	
	/**
	 * Support function for UnaryOperator<T>
	 */
	public static List<Integer> applyUnary(List<Integer> list, UnaryOperator<Integer> uo) {
		List<Integer> result = new ArrayList<>();
		for (Integer i : list) {
			result.add(uo.apply(i));
		}
		return result;
	}
	
	/**
	 * Support function for BinaryOperator<T>
	 */
	public static List<Integer> applyBinary(List<Integer> list, BinaryOperator<Integer> bo) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0, j = i + 1; i < list.size() - 1; i++, j++) {
			result.add(bo.apply(list.get(i), list.get(j)));
		}
		return result;
	}

}
