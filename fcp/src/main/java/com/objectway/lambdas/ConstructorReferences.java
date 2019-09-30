package com.objectway.lambdas;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.objectway.model.Apple;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Describes concepts behind Constructor References 
 * for Lambda Expressions.
 */
public class ConstructorReferences {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/*
		 * A Constructor Reference is a special form of Method Reference, but for Class constructors 
		 * Its syntax can be generalized with:
		 *      ClassName::new
		 * It basically works like the Method Reference for static methods.
		 * It can be used to build
		 */
		Supplier<Apple> s1 = Apple::new;
		Apple a1 = s1.get();
		
		/* which is equivalent to ... */
		Supplier<Apple> s2 = () -> new Apple();
		
		/* What about our Apple class and two and three parameters constructors? */
		BiFunction<String, Integer, Apple> bf = Apple::new;
		Apple a2 = bf.apply("red", 100);
		
		/* But we don't have a standard functional interface for three parameters. */
		// TriFunction<String, Integer, Double> tf = Apple::new;
		
		/* Implement TriFunction starting from the BiFunction<T, U, R> definition. */
	}

}

