package com.objectway.model;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * A simple bean that maintains state. This state is modified concurrently by some
 * parallel code and produces side effects. Used to show problems with bad concurrent code.
 * Kept simple on purpose.
 */
public class Accumulator {
	
	public long total = 0;
	
	public void add(long value) {
		total += value;
	}

}
