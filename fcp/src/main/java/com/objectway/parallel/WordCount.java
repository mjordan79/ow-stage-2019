package com.objectway.parallel;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.objectway.model.WordCounter;

public class WordCount {
	
	private static final Logger logger = LoggerFactory.getLogger(WordCounter.class.getName());

	private static final String SENTENCE = "A good day is always represented by a functional programming session!";
	
	public static void main(String[] args) {
		logger.info("Counting Words Iteratively: {}", iterativeWordCount(SENTENCE));
		logger.info("Counting Words Functionally: {}", functionalSequentialWordCount(SENTENCE));
		logger.info("Counting Words with Spliterator: {}", functionalParallelWordCount(SENTENCE));
	}
	
	/**
	 * Iteratively count words from a String.
	 */
	public static int iterativeWordCount(String str) {
		int counter = 0;
		boolean lastSpace = true;
		for (char c : str.toCharArray()) {
			if (Character.isWhitespace(c)) {
				lastSpace = true;
			} else {
				if (lastSpace) { 
					counter++;
					lastSpace = false;
				}
			}
		}
		return counter;
	}
	
	/**
	 * Functional sequential word count.
	 */
	public static int functionalSequentialWordCount(String str) {
		// Converting a String to a Stream requires some tricks.
		IntStream stream = IntStream.range(0, str.length());
		Stream<Character> words = stream.mapToObj(str::charAt);
	    WordCounter counter = words.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
	    return counter.getCounter();
	}
	
	/**
	 * Functional parallel word count.
	 */
	public static int functionalParallelWordCount(String str) {
		Spliterator<Character> spliterator = new WordCountSpliterator(str);
		Stream<Character> charStream = StreamSupport.stream(spliterator, true);
		WordCounter count = charStream.parallel()
				.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		return count.getCounter();
	}
}
