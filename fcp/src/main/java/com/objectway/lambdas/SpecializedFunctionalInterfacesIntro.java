package com.objectway.lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Describes basic principles of specialized Functional Interfaces.
 * 
 * @see java.util.function.IntPredicate
 * 
 * Additional Specializations:
 * For Predicate<T>:
 * @see java.util.function.LongPredicate
 * @see java.util.function.DoublePredicate
 * 
 * For Consumer<T>:
 * @see java.util.function.IntConsumer
 * @see java.util.function.LongConsumer
 * @see java.util.function.DoubleConsumer
 * 
 * For Function<T, R>:
 * @see java.util.function.IntFunction<R>
 * @see java.util.function.IntToDoubleFunction
 * @see java.util.function.IntToLongFunction
 * @see java.util.function.LongFunction<R>
 * @see java.util.function.LongToDoubleFunction
 * @see java.util.function.LongToIntFunction
 * @see java.util.function.DoubleFunction<R>
 * @see java.util.function.ToIntFunction<T>
 * @see java.util.function.ToDoubleFunction<T>
 * @see java.util.function.ToLongFunction<T>
 * 
 * For Supplier<T>:
 * @see java.util.function.BooleanSupplier
 * @see java.util.function.IntSupplier
 * @see java.util.function.LongSupplier
 * @see java.util.function.DoubleSupplier
 * 
 * For UnaryOperator<T>:
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 * 
 * For BinaryOperator<T>:
 * @see java.util.function.IntBinaryOperator
 * @see java.util.function.LongBinaryOperator
 * @see java.util.function.DoubleBinaryOperator
 * 
 * For BiConsumer<T, U>
 * @see java.util.function.ObjIntConsumer<T>
 * @see java.util.function.ObjLongConsumer<T>
 * @see java.util.function.ObjDoubleConsumer<T>
 * 
 * For BiFunction<T, U, R>
 * @see java.util.function.ToIntBiFunction<T, U>
 * @see java.util.function.ToLongBiFunction<T, U>
 * @see java.util.function.ToDoubleBiFunction<T, U>
 */
public class SpecializedFunctionalInterfacesIntro {
	
	private static final Logger logger = LoggerFactory.getLogger(SpecializedFunctionalInterfacesIntro.class.getName());

	public static void main(String[] args) {
		
		final int MAX_NUMBERS = 20_000_000;
		
		/* For massive processing of large collections, performance may suffer
		 * for autoboxing and autounboxing operations. Let's make an example 
		 * with a large ArrayList.
		 */
		List<Integer> massiveList = new ArrayList<>(MAX_NUMBERS);
		List<Integer> massiveList2 = new ArrayList<>(MAX_NUMBERS);
		logger.info("Building an ArrayList with {} autoboxed numbers.", 2 * MAX_NUMBERS);
		for (int i = 0; i < MAX_NUMBERS; i++) {
			massiveList.add(i); 
			massiveList2.add(i); 
		}
		logger.info("Done!");
		
		{ // We scope this code to make everything declared disposable by the GC.
			long start = System.currentTimeMillis();
			Predicate<Integer> p = (Integer i) -> i % 2 == 0;
			filterWithPredicate(massiveList, p);
			long end = System.currentTimeMillis();
			logger.info("Filtered {} numbers WITH autoboxing in {} ms.", MAX_NUMBERS, end - start);
		}
		
		{ // Let's try without autoboxing.
			long start = System.currentTimeMillis();
			IntPredicate p = (int i) -> i % 2 == 0;
			filterWithIntPredicate(massiveList2, p);
			long end = System.currentTimeMillis();
			logger.info("Filtered {} numbers WITHOUT autoboxing in {} ms.", MAX_NUMBERS, end - start);
		}

	}
	
	/**
	 * Filter a list of Integer types using autoboxing. The resulting list is build using primitive int.
	 * @param list 
	 * @param p the Lambda Expression used to filter.
	 * @return List<Integer> built from primitive int
	 */
	public static List<Integer> filterWithPredicate(List<Integer> list, Predicate<Integer> p) {
		List<Integer> result = new ArrayList<>();
		for (int i : list) {
			if (p.test(i))
				result.add(i);
		}
		return result;
	}
	
	/**
	 * Filter a list of Integer types using autoboxing. The resulting list is build using primitive int.
	 * @param list 
	 * @param p the Lambda Expression used to filter.
	 * @return List<Integer> built from primitive int
	 */
	public static List<Integer> filterWithIntPredicate(List<Integer> list, IntPredicate p) {
		List<Integer> result = new ArrayList<>();
		for (int i : list) {
			if (p.test(i))
				result.add(i);
		}
		return result;
	}

}
