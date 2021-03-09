package hu.ak_akademia.concurrent.prime_generator_parallel.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import hu.ak_akademia.concurrent.prime_generator_parallel.utility.PrimeMathUtility;

/**
 * The FindPrimesTask gets the list of primes found in the first phase of the application, and checkes if numbers between "from" and "to" fields are primes or not. 
 * @author Izabella Marton
 *
 */
class FindPrimesTask implements Callable<List<Long>> {

	private long from;
	private long to;
	private List<Integer> primesUnderSqRoot;

	FindPrimesTask(long from, long to, List<Integer> primesUnderSqRoot) {
		this.from = from;
		this.to = to;
		this.primesUnderSqRoot = primesUnderSqRoot;
	}

	@Override
	public List<Long> call() throws Exception {
		List<Long> primes = new ArrayList<>(PrimeMathUtility.highBoundForPrimeCount(from, to - 1));
		for (long i = from % 2 == 0 ? ++from : from; i < to; i += 2) {
			if (checkPrimes(i))
				primes.add(i);
		}
		return primes;
	}

	private boolean checkPrimes(long number) {
		for (Integer prime : primesUnderSqRoot) {
			if (number % prime == 0L)
				return false;
		}
		return true;
	}
}
