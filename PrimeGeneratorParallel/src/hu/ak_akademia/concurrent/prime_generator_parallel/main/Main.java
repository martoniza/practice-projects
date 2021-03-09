package hu.ak_akademia.concurrent.prime_generator_parallel.main;

/**
 * <h1>PrimeGeneratorParallel</h1>
 * <p>The PrimeGeneratorParallel program implements an application which generates a certain number of primes using concurrent programing.</p>
 * <p>User can set the limit (until what number the application search for primes), the number of tasks (the application divides the whole interval into smaller intervals) and the number of threads.<p>
 * <p>In the first phase the application searches for primes under the quare root of max limit and creates a list of primes found - this phase is implemented by one thread.</p>
 * <p>In the second phase more threads are created the primes are looked for by using the list of primes found in the first phase.</p>
 * <p><b>Note:</b>The aim of the program was to practice concurrent programing, especially the use of Executor Service, that's why I used a naive way of checking if numbers are primes or not.</p>
 * 
 * @author Izabella Marton
 */

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.ak_akademia.concurrent.prime_generator_parallel.executor.PrimeExecutorService;

/**
 * Main class of the application which manages the whole code (instantiates
 * classes, etc.), prints certain subgroups of the found primes (e.g. from a
 * certain index), welcome and farewall messages to the console.
 * 
 * @author Izabella Marton
 *
 */

public class Main {

	public static void main(String[] args) {

		try {
			new Main().run();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	void run() throws InterruptedException, ExecutionException {
		printWelcome();
		Input input = new Input();
		input.getParameters();
		System.out.printf("%nGeneration of primes is under process...%n");
		PrimeExecutorService executorService = new PrimeExecutorService(input);
		Instant start = Instant.now();
		List<Long> primes = executorService.run();
		printResults(primes, input.getMaxLimit(), Duration.between(start, Instant.now()));
		printPrimesFromIndexLoop(primes, input, 10);
		printFarewell();
	}

	private void printResults(List<Long> primes, long maxLimit, Duration duration) {
		int size = primes.size();
		System.out.printf("%nNumber of primes not higher than %,d: %,d%n", maxLimit, size);
		System.out.print("The first couple of primes: ");
		printPrimesFromIndex(primes, 1, 25);
		System.out.println();
		System.out.print("The last couple of primes: ");
		printPrimesFromIndex(primes, size + 1 - 10, 10);
		System.out.println();
		System.out.printf("%nGenerating primes took %.2f seconds%n%n", duration.toMillis() / 1000d);
	}

	private void printWelcome() {
		System.out.println("Welcome to the multi-threaded prime generator application!");
		System.out.println();
	}

	private void printFarewell() {
		System.out.println();
		System.out.println("Goodbye!");
	}

	private void printPrimesFromIndex(List<Long> primes, int index, int count) {
		System.out.print(primes.get(index - 1));
		for (int i = index, k = 1; k < count && i < primes.size(); i++, k++) {
			System.out.printf(", %d", primes.get(i));
		}
	}

	private void printPrimesFromIndexLoop(List<Long> primes, Input input, int count) {
		System.out.print("In order to use prime finder service, set the starting index from which you want to see primes printed out!");
		int startingIndex;
		while ((startingIndex = input.getStartinIndex(primes.size())) >= 0) {
			System.out.printf("    Primes found: ", startingIndex, 10);
			printPrimesFromIndex(primes, startingIndex, count);
		}
		;
	}

}
