package com.objectway.lambdas;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import com.objectway.behavioural.Apple;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Describes concepts behind Method References 
 * for Lambda Expressions.
 */
public class MethodReferences {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/*
		 * Method References allows to use existing method definitions and pass them in lamba expressions.
		 * They can be seen as a contract form for lambdas that call a specific method.
		 * In its shorteness, the have the advantage to call a method using its name rather than its definition.
		 * There are 3 forms of method references.
		 */
		
		{
			/* 1) Method References to a static method, eg. the parseInt() method of the class Integer */
			Function<String, Integer> fLambda = (String s) -> Integer.parseInt(s);
			/* ... becomes ... */
			Function<String, Integer> fMR = Integer::parseInt;
		}
		
		{
			/* 2) Method References to instance methods of arbitrary types, eg. lenght() method of the String class */
			Function<String, Integer> fLambda = (String s) -> s.length();
			/* ... becomes ... */
			Function<String, Integer> fMR = String::length;
		}
		
		{
			/* 3) Method References to instance methods of instantiated objects.
			 * eg. You have a variable containing a reference for an object of type Apple that has the method getColor() */
			Apple apple = new Apple("red", 150);
			Function<Apple, String> fLambda = (Apple a) -> a.getColor();
			/* ... becomes ... */
			Function<Apple, String> fMR = Apple::getColor;
		}
		
		/* Other examples */
		BiPredicate<List<String>, String> contains = (List<String> list, String element) -> list.contains(element);
		/* becomes ... */
		BiPredicate<List<String>, String> sContains = (list, element) -> list.contains(element);
		/* ... that becomes ... */
		BiPredicate<List<String>, String> mrContains = List::contains;
	}

}
