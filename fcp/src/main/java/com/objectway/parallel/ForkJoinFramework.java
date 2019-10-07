package com.objectway.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Renato Perini <renato.perini@objectway.com>
 * 
 * Fork-Join Framework is the parallel framework that parallel streams use underneath.
 * This parallel framework is an implementation of the ExecutorService interface that 
 * distributes parallel tasks in a thread pool called ForkJoinPool.
 * 
 * To submit tasks to this pool, it's necessary to extend the class RecursiveTask<V> 
 * where V is the type produced by the parallelized tasks. If the tasks don't produce
 * any result, the return type is RecursiveAction.
 * 
 * Each split originates 2 nodes of a binary tree.
 *                           [...]
 *                    [...]         [...]
 *                [...]   [...] [...]  [...]
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.RecursiveTask<V>
 * @see java.util.concurrent.RecursiveAction
 */
public class ForkJoinFramework extends RecursiveTask<Long> {
	
	private static final Logger logger = LoggerFactory.getLogger(ForkJoinFramework.class.getName());

	private static final long serialVersionUID = -1181868605574481758L;
	
	// The numbers to sum in parallel using the Fork-Join Framework.
	private final long[] numbers;
	
	// The indexes for the arry. Usually from 0 to array.length
	private final int start;
	private final int end;
	
	// This is the size of the array under which this task won't be split further.
	public static final long THRESHOLD = 10_000; 
	
	// Constructos.
	public ForkJoinFramework(long[] numbers) {
		this(numbers, 0, numbers.length);
	}
	
	/* Only usable from the main public constructor. It initializes the class with appropriate
	 * ranges.
	 */
	private ForkJoinFramework(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	/**
	 * The only method needed to be implemented from the RecursiveTask abstract class.
	 * This method must provide the business logic to split the task in sub-tasks and
	 * the algorithm to produce the result from a single task when it's no longer convenient
	 * or possible to split further.
	 */
	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) { // If the task is small enough or it's not possible to split further ...
			return computeSequentially();
		}
		
		// Split left to the first half.
		ForkJoinFramework leftTask = new ForkJoinFramework(numbers, start, start + length / 2);
		
		// Asynchronously execute the task in another thread of the ForkJoinPool.
		leftTask.fork();
		
		// Split right to the other half.
		ForkJoinFramework rightTask = new ForkJoinFramework(numbers, start + length / 2, end);
		
		// Executes this task synchrounously potentially creating a new split.
		Long rightResult = rightTask.compute();
		
		// Read the result of the first task or waits for it to be completed.
		Long leftResult = leftTask.join();
		
		
		return leftResult + rightResult;
	}
	
	// Sequential reduction of split chunks.
	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
	
	public static void main(String[] args) {
		
		// Let's initialize our big array containing long numbers.
		long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
		
		// Initilize the ForkJoinTask instantiating the class that implements the RecursiveTask.
		ForkJoinTask<Long> task = new ForkJoinFramework(numbers);
		
		// Initialize the ForkJoinPool. In real applications you won't need more tha one instance.
		ForkJoinPool threadPool = new ForkJoinPool();
		
		// Submit the task to the ForkJoinPool.
		long start = System.currentTimeMillis();
		long result = threadPool.invoke(task);
		long end = System.currentTimeMillis();
		
		// Yoo-hoo. Here is the result, ladies and gentlemen.
		logger.info("Result: {} computed in {} ms.", result, end - start);
	}
	
}
