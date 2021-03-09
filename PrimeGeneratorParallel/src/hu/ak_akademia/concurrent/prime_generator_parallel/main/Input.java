package hu.ak_akademia.concurrent.prime_generator_parallel.main;

import static hu.ak_akademia.concurrent.prime_generator_parallel.main.Constants.*;

/**
 *<p>The Input class manages all inputs: ask for the max limit, the number of intervals and the numbers of threads to be able to generate primes under max limit. Besides it asks for the starting index when the application prints out primes from an index to the console in a later phase of the application.</p>
 * 
 * @author Izabella Marton
 */

import java.util.Scanner;

public class Input {

	private final Scanner sc = new Scanner(System.in);
	private long maxLimit;
	private double sqRoot;
	private int intervalumCount;
	private int threadCount;

	void getParameters() {
		this.maxLimit = getInput((String.format("Set the max limit for searching (%,d ≤ max limit ≤ %,d)!",
				SEARCH_MIN_LIMIT, SEARCH_MAX_LIMIT)), "  Max limit = ", SEARCH_MIN_LIMIT, SEARCH_MAX_LIMIT);
		this.sqRoot = Math.sqrt(maxLimit + 1);
		int maxIntervCountToHaveRequiredIntervSize = (int) (maxLimit - Math.round(sqRoot)) / REQUIRED_INTERVAL_SIZE;
		this.intervalumCount = (int) getInput((String.format(
				"Set the number of task/intervals (prime search is carried out in smaller intervals) (%,d ≤ number of tasks ≤ %,d)!",
				MIN_INTERVAL_COUNT, MAX_INTERVAL_COUNT)), "  Number of tasks = ", MIN_INTERVAL_COUNT,
				MAX_INTERVAL_COUNT > maxIntervCountToHaveRequiredIntervSize ? maxIntervCountToHaveRequiredIntervSize
						: MAX_INTERVAL_COUNT);
		this.threadCount = intervalumCount == 1 ? 1 : (int) getInput//
		((String.format("Set the number of threads to be used (%,d ≤ number of threads ≤ %,d)!", MIN_THREAD_COUNT,
				MAX_THREAD_COUNT)), "  Numbers of threads = ", MIN_THREAD_COUNT,
				MAX_THREAD_COUNT > intervalumCount ? intervalumCount : MAX_THREAD_COUNT);
	}

	int getStartinIndex(int size) {
		int startingIndex;
		startingIndex = (int) getInput("", "  starting index: ", 1, size);
		return startingIndex;
	}

	long getInput(String prompt1, String prompt2, long min, long max) {
		System.out.println(prompt1);
		long input = 0;
		do {
			System.out.print(prompt2);
			try {
				input = Long.parseLong(sc.nextLine());
			} catch (NumberFormatException e) {
				if (prompt1.isEmpty()) {
					return -1L;
				} else {
					continue;
				}
			}
		} while (input < min || input > max);
		return input;
	}

	public long getMaxLimit() {
		return maxLimit;
	}

	public double getSqRoot() {
		return sqRoot;
	}

	public int getIntervalumCount() {
		return intervalumCount;
	}

	public int getThreadCount() {
		return threadCount;
	}

}
