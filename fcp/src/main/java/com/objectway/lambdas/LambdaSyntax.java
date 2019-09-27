package com.objectway.lambdas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * Just some basic syntax for Lambda Expressions. 
 * First, please @see com.objectway.lambdas.LambdaOverview 
 */
public class LambdaSyntax {
	
	private static final Logger logger = LoggerFactory.getLogger(LambdaSyntax.class.getName());

	public static void main(String[] args) {
		
		/* Generic Lambda syntax:
		 * (parameters) -> expression
		 * and:
		 * (parameters) -> { statements; }
		 * 
		 * Let's play a game. An abstract method for a functional interface corresponds
		 * to a Lambda Expression. Let's see.
		 */

		/* Abstract method:
		 * 
		 * int measure(String str) {
		 *     return str.lenght(); 
		 * }
		 * 
		 * Corresponds to:
		 * String str -> str.lenght()
		 */
		
		/* Abstract method:
		 * 
		 * boolean weight(Apple a) {
		 *     return a.getWeight() > 150;
		 * }
		 * 
		 * Corresponds to:
		 * (Apple a) -> a.getWeight() > 150
		 */
		
		/* Abstract method:
		 * 
		 * void sum(int x, int y) {
		 *     System.out.println("Sum: " + x + y); 
		 * }
		 * 
		 * Corresponds to:
		 * (int x, int y) -> {
		 *     System.out.println("Sum: " + x + y);
		 * }
		 */
		
		/* Abstract method:
		 * 
		 * int number() {
		 *     return 15; 
		 * }
		 * 
		 * Corresponds to:
		 * () -> 15
		 * 
		 * And it's equal to:
		 * () -> { return 15; }
		 * 
		 * because return is a statement.
		 */
		logger.info("No output, but you should really read my code.");
	}

}
