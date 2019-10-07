package com.objectway.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSum extends RecursiveTask<Long> {

	private static final long serialVersionUID = -1181868605574481758L;
	
	private final long[] numbers;
	private final int start;
	private final int end;
	
	public static final long THRESHOLD = 10_000;
	
	public ForkJoinSum(long[] numbers) {
		this(numbers, 0, numbers.length);
	}
	
	private ForkJoinSum(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int lenght = end - start;
		if (lenght <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSum leftTask = new ForkJoinSum(numbers, start, start + lenght / 2);
		leftTask.fork();
		ForkJoinSum rightTask = new ForkJoinSum(numbers, start + lenght / 2, end);
		Long rightResult = rightTask.compute();
		Long leftResult = leftTask.join();
		return leftResult + rightResult;
	}
	
	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
	
	public static void main(String[] args) {
		long n = 10_000_000;
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSum(numbers);
		long result = new ForkJoinPool().invoke(task);
		System.out.println("Result: " + result);
	}
}
