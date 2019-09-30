package com.objectway.lambdas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.lambdas.comparators.AppleComparator;
import com.objectway.model.Apple;

import static java.util.Comparator.comparing;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Building lambdas from traditional functional programming with anonymous inner classes
 * and behavioural parameterization moving to lambdas and method references.
 * This is also represents a menthal process to better engineer code using functional programming
 * and modern Java techniques. Think "traditional" and refactor to "modern".
 * 
 * We implement the problem of sorting apples by weight using a List<Apple> data structure and
 * the com.objectway.behavioural.Apple class.
 * First approach: Using a concrete class that's an implementation of a Comparator interface.
 * Second approach: Making the code more compact using a behavioural pattern and an anonymous inner class.
 * Third approach: Simplifying behavioural pattern passing only the sorting logic through a lambda expression.
 * Fourth: Reducing everything to one line using method reference and a static import for methods in functional interfaces.
 * 
 * @see com.objectway.model.Apple
 */
public class MentallyBuildingLambdas {

	private static final Logger logger = LoggerFactory.getLogger(MentallyBuildingLambdas.class.getName());
	
	public static void main(String[] args) {
		List<Apple> apples = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
			apples.add(new Apple("red", getRandomInt()));
			apples.add(new Apple("yellow", getRandomInt()));
		}
		
		Collections.shuffle(apples);
		
		// Preparation stuff has been done. Let's sort this beast.
		apples.sort(new AppleComparator());
		
		// Re-shuffles. Try a different approach. We use an anonymous inner class.
		Collections.shuffle(apples);
		apples.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}			
		});
		
		/* Comparator is a functional interface. Its functional descriptor is:
		 * int compare(T o1, T o2) so the lambda has the form (T, T) -> int
		 */
		apples.sort((Apple o1, Apple o2) -> o1.getWeight().compareTo(o2.getWeight()));
		Collections.shuffle(apples);
		
		// Re-structure lambda to a simple form.
		apples.sort((o1, o2) -> o1.getWeight().compareTo(o2.getWeight()));
		Collections.shuffle(apples);
		
		/* Comparator has a static method called comparing() that extracts a Comparable key (through a Function<T, R>). 
		 * We just need to extract the value for comparison.
		 */
		apples.sort(Comparator.comparing(a -> a.getWeight()));
		
		// With the static import, more compact.
		apples.sort(comparing(a -> a.getWeight()));
		
		// Convering to a method reference? 
		apples.sort(comparing(Apple::getWeight));
		
		// From a dedicated AppleComparator class to a one-liner using lambdas!
		logger.info("Done");
	}
	
	/**
	 * Helper function that returns a pseudorandom int in a range.
	 */
	private static int getRandomInt() {
		final int MIN = 60, MAX = 150;

		Random r = new Random();
		return r.nextInt((MAX - MIN) + 1) + MIN;
	}

}
